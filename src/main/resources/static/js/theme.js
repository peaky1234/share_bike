
    function mySubmit(form){
    // 定义formData对象
    let formData = new FormData(form);

    // 从formData对象中获取表单数据
    let username = formData.get('username');
    let password = formData.get('password');
    let data = {
    'username': username,
    'password': password
};

    // 发送AJAX请求
    $.ajax({
    url: '/login',
    type: 'GET',
    data: JSON.stringify(data),
    contentType: 'application/json',
    success: function (response) {
    if (response.code === 0) {
    window.location = 'index.html';
}
    alert(response.data);
}
});
}


var saveSelectColor = {
    'Name': 'SelcetColor',
    'Color': 'theme-black'
}



// 判断用户是否已有自己选择的模板风格
if (storageLoad('SelcetColor')) {
    $('body').attr('class', storageLoad('SelcetColor').Color)
} else {
    storageSave(saveSelectColor);
    $('body').attr('class', 'theme-black')
}


// 本地缓存
function storageSave(objectData) {
    localStorage.setItem(objectData.Name, JSON.stringify(objectData));
}

function storageLoad(objectName) {
    if (localStorage.getItem(objectName)) {
        return JSON.parse(localStorage.getItem(objectName))
    } else {
        return false
    }
}