var time_temp=['8:00', '8:05', '9:00', '9:05', '10:00', '10:05',
    '11:00','11:05','12:00'];
var data_temp=[25,26,25,21,20,25.1,25.3,27.1,20.1];
function tmp_echarts(){
    var line_echart=echarts.init(document.querySelector('.tmp_chart'));
    var option = {
        grid: {
            x: "5%",//x 偏移量
            y: "20%", // y 偏移量
        },
        title: {
            text:"实时温度折线图",
            subtext:"单位：℃",
            textStyle:{
                color:'rgb(153,153,153)',
                fontSize:14
            }
        },
        backgroundColor:'rgba(165,165,168,0)',
        tooltip: {
            trigger:"axis",
            axisPointer: {
                // 坐标轴指示器，坐标轴触发有效
                type: "line",// 默认为直线，可选为：'line' | 'shadow'
                axis:"x"
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: time_temp,
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
            splitLine:{
                show:false
            },
            axisLabel: {
                color:"#cecece"
            }
        },
        series: [{
            data: data_temp,
            type: 'line',
            smooth:true,
            symbol:"circle",
            showSymbol:false,
            lineStyle: {
                color: "#248ff7",
                width: 2
            },
            areaStyle: {
                color: new echarts.graphic.LinearGradient(
                    0,
                    0,
                    0,
                    1,
                    [
                        {
                            offset: 0,
                            color: "#248ff7"   // 渐变色的起始颜色
                        },
                        {
                            offset: 0.8,
                            color: "#5312ae99"   // 渐变线的结束颜色
                        }
                    ],
                    false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)"
            },
            // 设置拐点 小圆点
            symbol: "circle",
            // 拐点大小
            symbolSize: 1,
            // 设置拐点颜色以及边框
            itemStyle: {
                color: "#0184d5",
                borderColor: "rgba(221, 220, 107, .1)",
                borderWidth: 12
            }
        }],
        itemStyle: {
            color: "#0184d5",
            borderColor: "rgba(221, 220, 107, .1)",
            borderWidth: 12
        },
    };
    line_echart.setOption(option);
    window.addEventListener("resize",function(){

        line_echart.resize();   //myChart指自己定义的echartsDom对象

    });
}