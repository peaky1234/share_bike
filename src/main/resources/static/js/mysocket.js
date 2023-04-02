var stompClient;
var dt=new Date();
var bool=1;//标志可调灯
var tem=0;
var hu=0;
var il=0;
var last_num=0;
var int;

var status="01";
var slider1 = layui.slider;

var jsonData=[];

var myTable={}

$(function($) {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
//初始温度
        stompClient.subscribe("/topic/First_temp",function (msg){
            //document.querySelector(".init-tmp-number1").innerHTML=parseFloat(msg['body']);
            localStorage.setItem("startTemp",JSON.stringify(msg['body']));
            updateVueData('startTemp',JSON.stringify(msg['body']));

        });


//单车和电池的id信息
        stompClient.subscribe("/topic/by",function (msg){
            localStorage.setItem("bicycle",msg['body'])
            updateVueData('bicycle');
        });

        stompClient.subscribe("/topic/ba",function (msg){
            localStorage.setItem("battery",msg['body'])
            updateVueData('battery');
        });

//位置信息
        stompClient.subscribe("/topic/po",function (msg){
            // document.querySelector(".position").innerHTML=msg['body'];
            localStorage.setItem("position",msg['body'])
            updateVueData('position');
        });
        stompClient.subscribe("/topic/te",function (msg) {
            var strList=msg['body'].split("s");
            for(var i=0;i<strList.length-1;i++){
                var h=JSON.parse(strList[i].substring(0,4));
                var v=JSON.parse(strList[i].substring(4,8));
                jsonData.push({"hum":h,"voltage":v})
            }
            // layer.open({
            //     type: 2,
            //     title: '重发数据',
            //     maxmin: true,
            //     skin: 'layui-layer-lan',
            //     shadeClose: true, //点击遮罩关闭层
            //     area: ['800px', '620px'],
            //     content: 'test.html',//弹框显示的url,对应的页面
            //     success: function (layero, index) {  //layero是子页面的整个DOM元素
            //         var iframeWin = window[layero.find('iframe')[0]['name']]; //获取子页面的窗口对象
            //         iframeWin.setTableData(jsonData); //调用子页面的方法，并将要传递的数据作为参数传递
            //     }
            // });
        });

//故障显示
        stompClient.subscribe("/topic/mu",function (msg){
            layer=layui.layer;
           if(msg['body']==='1'){
               layui.use('layer',function(){
                   layer.msg('温差过大，请立即停车',{
                       icon:2
                   });
               })
           }

           else if(msg['body']==='2'){
               layui.use('layer',function(){
                   layer.msg('电压过低，请立即停车',{
                       icon:2
                   });
               })
           }
        });


//车速
        stompClient.subscribe("/topic/sp",function (msg){
            document.querySelector(".speed_control").value=msg['body'];
            document.querySelector(".selected").innerHTML=msg['body'];
            localStorage.setItem('speed',msg['body']);
            updateSpeed();
        });


//电插锁
        stompClient.subscribe("/topic/switch",function (msg){
            if(msg['body']==='on'){
                openLock();
                localStorage.setItem('lock','1');
            }else{
                 closeLock();
                localStorage.setItem('lock','0');
            }
        });
//可调灯
        stompClient.subscribe("/topic/ke",function (msg){
            if(JSON.parse(msg['body'])===1){
                 openLight();
                 localStorage.setItem('light','1');
            }else{
                 closeLight();
                localStorage.setItem('light','0');
            }
        });
//蜂鸣器
        stompClient.subscribe("/topic/buzzer",function (msg){
            if(JSON.parse(msg['body'])===1){
                openBuzzer();
                localStorage.setItem('buzzer','1');
            }else{
                closeBuzzer();
                localStorage.setItem('buzzer','0');
            }
        })
//转向灯
        stompClient.subscribe("/topic/led",function (msg){
            if(JSON.parse(msg['body'])===1){
                openLeft();
                localStorage.setItem('led','1');
            }else if(JSON.parse(msg['body'])===2){
                openRight();
                localStorage.setItem('led','2');
            }else{
                closeLeft();
                closeRight();
                localStorage.setItem('led','0');
            }
        })
 //光照度
        stompClient.subscribe("/topic/ill",function (msg){
            //document.querySelector(".ill").innerHTML=parseFloat(msg['body']);
            localStorage.setItem("ill",JSON.stringify(msg['body']));
            updateVueData('ill',JSON.stringify(msg['body']));
            // document.querySelector(".card_lux h2").innerHTML=parseFloat(msg['body']);
            il=parseFloat(msg['body']);
            if(document.querySelector(".light_chart")){
                var chart_ill=echarts.init(document.querySelector(".light_chart"));
                data_ill.shift();
                data_ill.push(msg['body']);
                time_ill.shift();
                time_ill.push(dt.getHours()+':'+dt.getMinutes());
                var option = {
                    xAxis:{
                        data: time_ill
                    },
                    series:[{
                        data: data_ill
                    }]
                }
                chart_ill.setOption(option);
            }
        });
//电压值
        stompClient.subscribe("/topic/vo",function (msg){
            //document.querySelector(".voltage").innerHTML=parseFloat(msg['body']);
            // document.querySelector(".card_lux h2").innerHTML=parseFloat(msg['body']);
            localStorage.setItem("voltage",JSON.stringify(msg['body']));
            updateVueData('voltage',JSON.stringify(msg['body']));
            if(document.querySelector(".VCC_chart")){
                var chart_ill=echarts.init(document.querySelector(".VCC_chart"));
                data_vo.shift();
                data_vo.push(msg['body']);
                time_vo.shift();
                time_vo.push(dt.getHours()+':'+dt.getMinutes());
                var option = {
                    xAxis:{
                        data: time_vo
                    },
                    series:[{
                        data: data_vo
                    }]
                }
                chart_ill.setOption(option);
            }
        });
        stompClient.subscribe("/topic/temp",function (msg){
            localStorage.setItem("temp",JSON.stringify(msg['body']));
            updateVueData('temp',JSON.stringify(msg['body']));
            if(document.querySelector(".tmp_chart")){
                var chart=echarts.init(document.querySelector(".tmp_chart"));
                data_temp.shift();
                data_temp.push(msg['body']);
                time_temp.shift();
                time_temp.push(dt.getHours()+':'+dt.getMinutes());
                var option = {
                    xAxis:{
                        data: time_temp
                    },
                    series:[{
                        data: data_temp
                    }]
                }
                chart.setOption(option);
            }
        });
    });
});







    /**
     页面设置车速
     */
    $(".speed_control").change(function(){

        bool=1;
    });
if( document.querySelector(".speed_control"))
    document.querySelector(".speed_control").addEventListener("input", () => {
        localStorage.setItem('speed',document.querySelector(".speed_control").value);
        if(document.querySelector(".speed_control").value=="0")
            var msg="000";
        else if(document.querySelector(".speed_control").value<100)
            var msg="0"+document.querySelector(".speed_control").value;
        else
            var msg=document.querySelector(".speed_control").value;
        if(bool==1){
            stompClient.send("/app/control",{},"Hwcal"+status+"03"+msg+"T");
            bool=0;
        }
        else {
            stompClient.send("/app/control",{},"Hwcal"+status+"03"+msg+"T");
        }
    });

    /**
     * 光照度阈值
     *
     */

    if(document.querySelector(".button1"))
    document.querySelector(".button1").onclick = function () {

        var msg = "000000";
        msg += document.querySelector("#threshold").value;
        while (msg.length > 6) {
            msg = msg.substr(1);
        }
        msg = "Hwclu0106" + msg + "T";
        stompClient.send("/app/control", {}, msg);
}



// 锁定点击
$(".img-lock1").click(function() {
    // display: block;
    $(".img-lock1").css("display", "none");
    $(".img-lock2").css("display", "block");
    stompClient.send("/app/control", {}, "Hwcel"+status+"02onT");
    localStorage.setItem('lock','1');
})
$(".img-lock2").click(function() {
    // display: block;
    $(".img-lock1").css("display", "block");
    $(".img-lock2").css("display", "none");
    stompClient.send("/app/control", {}, "Hwcel"+status+"03offT");
    localStorage.setItem('lock','0');
})

// 鸣笛更改
$(".img-lock5").click(function() {
    // display: block;
    $(".img-lock5").css("display", "none");
    $(".img-lock6").css("display", "block");
    stompClient.send("/app/control", {}, "Hwcho"+status+"011T");
    localStorage.setItem('buzzer','1');
})
$(".img-lock6").click(function() {
    // display: block;
    $(".img-lock5").css("display", "block");
    $(".img-lock6").css("display", "none");
    stompClient.send("/app/control", {}, "Hwcho"+status+"010T");
    localStorage.setItem('buzzer','0');
})

// 左转
$(".img-lock7").click(function() {
    // display: block;
    if(document.querySelector('.img-lock10').style.display!=="block") {
        $(".img-lock7").css("display", "none");
        $(".img-lock8").css("display", "block");
    }
    stompClient.send("/app/control", {}, "Hwcle01011T");
    localStorage.setItem('led','1')
})
$(".img-lock8").click(function() {
    // display: block;
    $(".img-lock7").css("display", "block");
    $(".img-lock8").css("display", "none");
    stompClient.send("/app/control", {}, "Hwcle01010T");
    localStorage.setItem('led','0')
})
// 右转
$(".img-lock9").click(function() {
    // display: block;
    if(document.querySelector('.img-lock8').style.display!=="block") {
        $(".img-lock9").css("display", "none");
        $(".img-lock10").css("display", "block");
        stompClient.send("/app/control", {}, "Hwcle01012T");
        localStorage.setItem('led','2')
    }
})
$(".img-lock10").click(function() {
    // display: block;
    $(".img-lock9").css("display", "block");
    $(".img-lock10").css("display", "none");
    stompClient.send("/app/control", {}, "Hwcle01010T");
    localStorage.setItem('led','0')
})



