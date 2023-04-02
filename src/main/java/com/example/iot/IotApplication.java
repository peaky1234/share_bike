package com.example.iot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Slf4j
@SpringBootApplication(scanBasePackages = "com.example.iot")
@ServletComponentScan   //开启 扫描过滤器
public class IotApplication {



    public static void main(String[] args) {
        SpringApplication.run(IotApplication.class, args);
        log.info("项目启动成功");
    }

}
