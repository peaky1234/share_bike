package com.example.iot.socket;

import com.example.iot.entity.Temp;
import com.example.iot.entity.employee;
import com.example.iot.entity.recording;
import com.example.iot.service.informationService;
import com.example.iot.service.loginService;
import com.example.iot.service.recordService;
import com.example.iot.service.sensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by blue_code on 2021/7/19 13:00
 * 客户端连接
 */

@Service
@EnableScheduling
@Controller
public class ClientSource implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(getClass());
    static String temp;
    String vo; //电压
    String ill;
    String po="0000";
    String num; //rfid编号
    String Ba_num="00",By_num="00";
    float last_temp = -100, last_ill = -100, last_vo = -100;
    Temp temp1 = new Temp();
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private sensorService sensor;
    @Autowired
    private informationService information;
    @Autowired
    private recordService recordservice;
    @Autowired
    private loginService login;
    recording record=new recording();
    employee employ=new employee();
    String by_status="00";String ba_status="00";
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");

    String username;
    int count=0;
    /***
     * socket连接
     * ***/
    private static HashMap<Integer, Usernet> map = new HashMap<>();//映射关系


    public void run(String[] args) throws Exception {
        try {
            ServerSocket ss = new ServerSocket(30000);//服务端的套接字

            System.out.println("服务器启动成功");
            while (true) {
                Socket socket = ss.accept();
                System.out.println("连接成功");
                Usernet use = new Usernet(map.size() + 1, socket);//存储socket
                map.put(use.getId(), use);
                use.send("你的客户端编号" + use.getId(),map);
                new Thread(() -> {
                    try {
                        InputStream is = use.getSocket().getInputStream();
                        byte[] bte = new byte[10 << 10];
                        int l = -1;
                        while (true) {
                            if (is.available() > 0) {
                                l = is.read(bte);
                                String content=new String(bte, 0, l);
                                sendMessagge(content);
                                //logger.info(content);
                                String type = content.substring(3, 5);
                                //HwdIDby01
                                if(type.equals("ID")){
                                    String type1 = content.substring(5, 7);
                                    if(type1.equals("by")){
                                        num=content.substring(9,17);
                                        By_num=information.sel_num(num,"单车");
                                        if(By_num!=null){
                                            record.setNumber(By_num);
                                            sendMessagge("Hwdby"+By_num+"T");
                                            simpMessagingTemplate.convertAndSend("/topic/by", By_num);
                                            by_status=By_num;
                                        }
                                        else
                                          logger.info("查询不到相关信息");
                                    }
                                    else {
                                        num=content.substring(9,17);
                                        Ba_num=information.sel_num(num,"电池");
                                        if(Ba_num!=null){
                                            sendMessagge("Hwdba"+Ba_num+"T");
                                            simpMessagingTemplate.convertAndSend("/topic/ba", Ba_num);
                                        }
                                        else
                                            logger.info("查询不到相关信息");
                                    }

                                }
                                    switch (type) {  //记得写break
                                        case "zc"://Hwdzc01username.password.phone
                                            String message=content.substring(7);
                                            System.out.println(message);
                                            String [] list;
                                            list=message.split("\\.");
                                            //System.out.println(list[0]);
                                            employ.setUsername(list[0]);
                                            employ.setPassword(list[1]);
                                            employ.setPhone(list[2]);
                                            employ.setPost("用户");
                                            login.save(employ);
                                            break;
                                        case "us": //Hwdus01username
                                            username=content.substring(7);
                                            break;
                                        case "he":      //温湿度  Hwdhe0127.1+1.68T
                                            temp = content.substring(7, 11);
                                            vo = content.substring(12, 16);
                                            simpMessagingTemplate.convertAndSend("/topic/temp", temp);//websocket推送数据 到前台
                                            simpMessagingTemplate.convertAndSend("/topic/vo", vo);
                                            break;
                                        //Hwcho01011T
                                        case "ho":
                                            simpMessagingTemplate.convertAndSend("/topic/buzzer",content.substring(9,10));
                                            break;
                                        //Hwcel0102onT
                                        case "el":
                                            String switch_1 = content.substring(9, 11);
                                            simpMessagingTemplate.convertAndSend("/topic/switch", switch_1);
                                            if(switch_1.equals("on")){
                                                count=0;
                                                record.setNumber(By_num);
                                                Date dateOn=new Date();
                                                record.setStart(formatter.format(dateOn));
                                                record.setUsername(username);
                                            }else{
                                                Date dateOff=new Date();
                                                record.setEnd(formatter.format(dateOff));
                                                record.setRepair(count);
                                                recordservice.save(record);
                                            }
                                            break;
                                        case "ie":      //Hwdie0106002000T
                                            ill = content.substring(9, 15);
                                            //int i = Integer.parseInt(ill);
                                            simpMessagingTemplate.convertAndSend("/topic/ill", ill);
                                            break;
                                        case "al"://Hwdal0103080T
                                            String speed = content.substring(9, 12);
                                            simpMessagingTemplate.convertAndSend("/topic/sp", speed);
                                            break;
                                        case "le"://Hwdle01011T
                                                String led = content.substring(9, 10);
                                                simpMessagingTemplate.convertAndSend("/topic/led", led);
                                            break;
                                        case "po": //Hwdpo010000T
                                            po = content.substring(7, 11);
                                            simpMessagingTemplate.convertAndSend("/topic/po", po);
                                            break;
                                        case "ke":  //Hwdke011T
                                            String ke = content.substring(7, 8);
                                            simpMessagingTemplate.convertAndSend("/topic/ke", ke);
                                            break;
                                        //Hwdte0123.41.64s23.41.63s23.41.64s  温度电压
                                        case "te":
                                            String reconnect=content.substring(7);
                                            simpMessagingTemplate.convertAndSend("/topic/te", reconnect);
                                            String [] reconnectList=reconnect.split("s");
                                            for(int i=0;i<reconnectList.length-1;i++){
                                                String reconnectTemp = reconnectList[i].substring(0, 4);
                                                String reconnectVo = reconnectList[i].substring(4);
                                                Date date=new Date();
                                                temp1.setType("温度传感器");
                                                temp1.setNumber(by_status);
                                                temp1.setValue(reconnectTemp);
                                                temp1.setStatus(1);
                                                temp1.setTime(format.format(date));
                                                sensor.save(temp1);
                                                temp1.setType("电压值");
                                                temp1.setValue(reconnectVo);
                                                sensor.save(temp1);
                                            }
                                            break;
                                        case "ti"://Hwdti01000600s000200s
                                            String connect=content.substring(7);
                                            simpMessagingTemplate.convertAndSend("/topic/ti", connect);
                                            String [] connectList=connect.split("s");
                                            for(int i=0;i<connectList.length;i++) {
                                                temp1.setType("光照传感器");
                                                temp1.setNumber(by_status);
                                                temp1.setValue(connectList[i]);
                                                temp1.setStatus(1);
                                                Date date=new Date();
                                                temp1.setTime(format.format(date));
                                                sensor.save(temp1);
                                            }
                                            break;
                                        case "mu":
                                            if(content.charAt(9) != '0'){
                                                count++;
                                                information.addRepair(Integer.valueOf(By_num));
                                            }
                                            simpMessagingTemplate.convertAndSend("/topic/mu", content.substring(9, 10));
                                            break;
                                    }

                                if(temp!=null&&(Math.abs(Float.parseFloat(temp)-last_temp))>0.3){
                                    Date date=new Date();
                                    temp1.setType("温度传感器");
                                    temp1.setValue(temp);
                                    temp1.setNumber(by_status);
                                    temp1.setTime(formatter.format(date));
                                    sensor.save(temp1);
                                    last_temp=Float.parseFloat(temp);
                                }
                                if(ill!=null&&(Math.abs(Float.parseFloat(ill)-last_ill))>10){
                                    Date date=new Date();
                                    temp1.setType("光照传感器");
                                    temp1.setValue(ill);
                                    temp1.setNumber(by_status);
                                    temp1.setTime(formatter.format(date));
                                    sensor.save(temp1);
                                    last_ill=Float.parseFloat(ill);
                                }
                                if(vo!=null&&(Math.abs(Float.parseFloat(vo)-last_vo))>0.3){
                                    Date date=new Date();
                                    temp1.setType("电压值");
                                    temp1.setValue(vo);
                                    temp1.setNumber(by_status);
                                    temp1.setTime(formatter.format(date));
                                    sensor.save(temp1);
                                    last_vo=Float.parseFloat(vo);
                                }
                            }
                            Thread.sleep(100);
                        }
                    } catch (IOException ignored ) {


                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }

                }).start();
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @MessageMapping("/control")
    public void sendMessagge(String message) throws IOException {
        logger.info(message);
            int si = map.size();
            for (int i = 1; i <= si; i++) {
                Usernet u = new Usernet();
                    u = (Usernet) map.get(i);
                    u.send(message,map);
            }
        }




}

