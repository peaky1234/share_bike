var data_vo=[25,26,25,21,20,25.1,25.3,27.1,27.1,29.1];
var time_vo=['8:00', '8:05', '9:00', '9:05', '10:00', '10:05', '11:00',
    '11:05','12:00','12:05'];
function VCC_echarts(){
    var straight_echart = echarts.init(document.querySelector(".VCC_chart"));
    var option = {
        backgroundColor:'rgba(165,165,168,0)',
        color:"rgba(255,255,255,0)",
        grid: {
            x: "5%",//x 偏移量
            y: "20%", // y 偏移量
        },
        title: {
            text:"电压变化图",
            subtext:"单位：RH",
            textStyle:{
                color:"#a5a5a5",
                fontSize: 13
            }
        },
        tooltip: {
            trigger:"axis",
            axisPointer: {
                // 坐标轴指示器，坐标轴触发有效
                type: "line",// 默认为直线，可选为：'line' | 'shadow'
                axis:"x",
            }
        },
        xAxis: {
            type: 'category',
            data: time_vo,
            axisTick: {
                show: false // 去除刻度线
            },
            axisLine: {
                show: false // 去除轴线
            },
            axisLabel: {
                color:"#cecece"
            }
        },
        yAxis: {
            type: 'value',
            min:'dataMin',
            axisTick: {
                show: false // 去除刻度线
            },
            axisLine: {
                show: false // 去除轴线
            },
            axisLabel: {
                color:"#cecece"
            },
            splitLine:{
                show:false
            }
        },
        series: [{
            data: data_vo,
            type: 'line',
            smooth: false,
            symbol:"circle",
            // 数据每一点的显示
            showSymbol:true,
            lineStyle: {
                // color: "#5312ae99",
                color:"#5312ae",
                width: 2
            },
            // 设置拐点 小圆点
            symbol: "circle",
            // 拐点大小
            symbolSize: 5,
            // 设置拐点颜色以及边框
            itemStyle: {
                color:"#248ff7",
                borderColor: "rgba(221, 220, 107, .1)",
                borderWidth: 12
            }
        }]
    };
    straight_echart.setOption(option);
    window.addEventListener("resize",function(){

        straight_echart.resize();   //myChart指自己定义的echartsDom对象

    });
}