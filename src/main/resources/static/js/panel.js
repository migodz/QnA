$(document).ready(function () {
    $("#questionBtn").click(function () {
        $("#questionBtn").attr("disabled",true);
        $("#questionBtn").text("请稍等..")
        $("#questionBtn").parents().filter("form").trigger("submit");
    });

    $("#question").keyup(function () {
        var word_num = $("#question").val().length;
        $("#word_num").text("字数：" + word_num + "/500");
        if (word_num > 500) {
            $("#word_num").addClass("text-danger");
            $("#word_num").removeClass("text-muted");
            $("#questionBtn").attr("disabled",true);

        }else{
            $("#word_num").addClass("text-muted");
            $("#word_num").removeClass("text-danger");
            $("#questionBtn").removeAttr("disabled");
        }
    });
});