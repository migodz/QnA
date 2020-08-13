$(document).ready(function () {
    $("#profile_picture_input").change(function () {
        if ($(this).val() != "")
            $('#profile_pic_submit_btn').attr("disabled", false);
        else
            $('#profile_pic_submit_btn').attr("disabled", true);
    });

    $("#reset_password_submit").click(function () {
        //判断密码是否合法
        var password = $("#new_password").val();
        var reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/
        if (!reg.test(password)) {
            $("#new_password").addClass("is-invalid");
            return false;
        } else {
            $("#new_password").removeClass("is-invalid");
        }

        //检查密码是否一致
        var password_confirm = $("#new_password_confirm").val();
        if (password_confirm != password) {
            $("#new_password_confirm").addClass("is-invalid");
            return false;
        } else {
            $("#new_password_confirm").removeClass("is-invalid");
        }
    });
});