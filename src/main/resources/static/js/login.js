var userLoginname = $("#userLoginname").val();
var userPwd = hex_md5($("#userPwd").val()); // 对密码进行MD5码加密
var authcode = $("#authcode").val();
$.ajax({
    type: 'POST',
    contentType: 'application/x-www-form-urlencoded;charset=UTF-8', // 发送信息至服务器时内容编码类型
    url: '../user/login',
    async: false, // 需要同步请求数据
    data: {
        userLoginname: userLoginname,
        userPwd: userPwd,
        authcode: authcode,
        checkCode: sessionStorage.getItem("checkCode")
    },
    dataType: 'json',
    success: function (data) {
        if (data.ret) {
        }
    }
}