$(document).ready(function () {
    $("#submit").click(function () {
        //判断用户名长度和内容
        var username = $("#username").val();
        var reg = /^[a-zA-Z0-9_-]{3,16}$/
        if (!reg.test(username)) {
            $("#username").addClass("is-invalid");
            return false;
        } else {
            $("#username").removeClass("is-invalid");
        }

        //判断密码是否合法
        var password = $("#password").val();
        reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/
        if (!reg.test(password)) {
            $("#password").addClass("is-invalid");
            return false;
        } else {
            $("#password").removeClass("is-invalid");
        }

        //检查密码是否一致
        var password_confirm = $("#password_confirm").val();
        if (password_confirm != password) {
            $("#password_confirm").addClass("is-invalid");
            return false;
        } else {
            $("#password_confirm").removeClass("is-invalid");
        }
    });
});