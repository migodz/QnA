$(document).ready(function () {
    $(".answerBtn").click(function () {
        $(this).attr("disabled",true);
        $(this).text("请稍等..")
        $(this).parents().filter("form").trigger("submit");
    });

    $("#answerText").keyup(function () {
        var word_num = $("#answerText").val().length;
        $("#word_num").text("字数：" + word_num + "/800");
        if (word_num > 800) {
            $("#word_num").addClass("text-danger");
            $("#word_num").removeClass("text-muted");
            $("#answerText").attr("disabled",true);

        }else{
            $("#word_num").addClass("text-muted");
            $("#word_num").removeClass("text-danger");
            $("#answerText").removeAttr("disabled");
        }
    });

    $("#answerText2").keyup(function () {
        var word_num = $("#answerText2").val().length;
        $("#word_num2").text("字数：" + word_num + "/800");
        if (word_num > 800) {
            $("#word_num2").addClass("text-danger");
            $("#word_num2").removeClass("text-muted");
            $("#answerText2").attr("disabled",true);

        }else{
            $("#word_num2").addClass("text-muted");
            $("#word_num2").removeClass("text-danger");
            $("#answerText2").removeAttr("disabled");
        }
    });
});