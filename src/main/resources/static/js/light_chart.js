var data_ill=[25,26,25,25,25,25.1,25.3,27.1,24.5,24.7,26,28,30,25];
var time_ill=['8:00', '8:05', '9:00', '9:05', '10:00', '10:05', '11:00',
    '11:05','12:00','12:05','13:00','13:05','14:00','14:05'];
function light_echarts(){
    var bar_echart = echarts.init(document.querySelector(".light_chart"));
    var option = {
        backgroundColor:'rgba(255,255,255,0)',
        color:"#5312ae",
        grid: {
            x: "5%",//x 偏移量
            y: "20%", // y 偏移量
        },
        title: {
            text:"实时光照变化图",
            subtext:"单位：LUX",
            textStyle:{
                color:'#a5a5a5',
                fontSize: 13
            }
        },
        tooltip: {
            trigger:"axis",
            axisPointer: {
                // 坐标轴指示器，坐标轴触发有效
                type: "shadow",// 默认为直线，可选为：'line' | 'shadow'
                axis:"x",
            }
        },
        xAxis: {
            type: 'category',
            data: time_ill,
            axisTick: {
                show: false // 去除刻度线
            },
            axisLine: {
                show: false // 去除轴线
            },
            axisLabel: {
                color:"rgba(162,162,162,0.5)"
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
                color:"rgba(162,162,162,0.5)"
            },
            splitLine:{
                show:false
            }
        },
        series: [{
            data: data_ill,
            type: 'bar',
            showBackground: true,
            backgroundStyle: {
                color: 'rgba(0,0,0,0)'
            },
            itemStyle: {
                // 修改柱子圆角
                barBorderRadius: 5,

                // 各个圆柱不同颜色
                // color:function(params){
                //   var colorlist = ['#DA251D','#E67716','#FBC300','#11440f','#32585a','#00ff77'];
                //          return colorlist[params.dataIndex];
                // }

                //圆柱颜色渐变
                color:new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: '#248ff7'
                }, {
                    offset: 1,
                    color: '#5312ae'
                }]),
            }
        }]
    };
    bar_echart.setOption(option);
    window.addEventListener("resize",function(){

        bar_echart.resize();   //myChart指自己定义的echartsDom对象

    });
}