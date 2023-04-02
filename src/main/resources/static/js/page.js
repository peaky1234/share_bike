// $.ajax({
//     url: 'http://localhost:8080/clear',
//     method: 'POST',
//     contentType: 'application/json',
//     success: function (response) {
//         if(response.code === 0){
//             //程序运行时将localStorage的数据全删除，但是后面刷新页面 要保证页面的数据不重置 后端只会允许第一次删除数据
//             localStorage.clear();
//             localStorage.setItem('username',user['username']);
//             //页面加载vue很快 所以需要更新数据 此时数据全为空
//             const keys = Object.keys(app.$data);
//             keys.forEach(key => {
//                 app[key] = null; // 或者 app[key] = '';
//             });
//                 app['speed']=null;
//         }else{
//             if(localStorage.getItem('lock')==="1") openLock();
//             else closeLock();
//
//             if(localStorage.getItem('light')==="1") openLight();
//             else closeLight();
//
//             if(localStorage.getItem('buzzer')==="1") openBuzzer();
//             else closeBuzzer();
//
//             if(localStorage.getItem('led')==="1") openLeft();
//             else if(localStorage.getItem('led')==="2") openRight();
//             else{
//                 closeLeft();
//                 closeRight()
//             }
//             updateSpeed();
//         }
//     },
// });

//需要注意的一个点就是localstorage储存的数据不会自动删除 会一直保存 所以需要我们自己去删除

var app=new Vue({
    el:"#page",
    data: {
        //localStorage储存的数据都是字符串类型 带双引号，需要利用JSON.parse()把其变成js格式
        temp: JSON.parse(localStorage.getItem('temp')),
        position: JSON.parse(localStorage.getItem('position')),
        //differenceTemp: JSON.parse(localStorage.getItem('differenceTemp')),
        ill: JSON.parse(localStorage.getItem('ill')),
        voltage: JSON.parse(localStorage.getItem('voltage')),
        hum: JSON.parse(localStorage.getItem('hum')),
        bicycle:JSON.parse(localStorage.getItem('bicycle')),
        battery:JSON.parse(localStorage.getItem('battery')),
    }
});

var sp=new Vue({
    el:".selected",
    data: {
        speed:0
    }
})


//更新vue实例
function updateVueData(key){
    app[key] = JSON.parse(localStorage.getItem(key));
}

function updateSpeed(){
    sp['speed']=JSON.parse(localStorage.getItem('speed'))
}



var img1 = document.querySelector(".img-lock1");
var img2 = document.querySelector(".img-lock2");
var img3 = document.querySelector(".img-lock3");
var img4 = document.querySelector(".img-lock4");
var img5 = document.querySelector(".img-lock5");
var img6 = document.querySelector(".img-lock6");
var img7 = document.querySelector(".img-lock7");
var img8 = document.querySelector(".img-lock8");
var img9 = document.querySelector(".img-lock9");
var img10 = document.querySelector(".img-lock10");
function openLock(){
        img1.style.display = "none";
        img2.style.display = "block";
}
function closeLock(){
        img1.style.display = "block";
        img2.style.display = "none";
}
function openLight(){
        img3.style.display = "none";
        img4.style.display = "block";
}
function closeLight(){
        img3.style.display = "block";
        img4.style.display = "none";
}
function openBuzzer(){
        img5.style.display = "none";
        img6.style.display = "block";
}
function closeBuzzer(){
        img5.style.display = "block";
        img6.style.display = "none";
}
function openLeft(){
    img7.style.display = "none";
    img8.style.display = "block";
}
function closeLeft(){
    img7.style.display = "block";
    img8.style.display = "none";
}
function openRight(){
    img9.style.display = "none";
    img10.style.display = "block";
}
function closeRight(){
    img9.style.display = "block";
    img10.style.display = "none";
}

if(localStorage.getItem('lock')==="1") openLock();
else closeLock();

if(localStorage.getItem('light')==="1") openLight();
else closeLight();

if(localStorage.getItem('buzzer')==="1") openBuzzer();
else closeBuzzer();

if(localStorage.getItem('led')==="1") openLeft();
else if(localStorage.getItem('led')==="2") openRight();
else{
    closeLeft();
    closeRight()
}
updateSpeed();