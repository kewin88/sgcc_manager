$(function () {
    $("#loginDialog").dialog({ minHeight: 200, minWidth: 350 });
    $("#login").click(function () {
        $("#message").text("");
        if ($("#username").val() == "" || $("#password").val() == "") {
            $("#message").text("用户名或密码不能为空！");
        }
        else {
            $.ajax(
                {
                    type: 'POST',
                    url: "Login.aspx/userloginValidate",
                    data: "{userloginZh:'" + $("#username").val() + "',userloginPwd:'" + $("#password").val() + "'}",
                    contentType: "application/json; charset=utf-8",
                    datatype: "json",
                    success: function (msg) {
                        var str = eval("(" + msg + ")");
                        if (str.d == "0") {
                            $("#message").text("用户名或密码不正确！");
                            $("#username").attr("value", "");
                            $("#password").attr("value", "");
                            $("#username").focus();
                        }
                        else {
                            location.href = "Default.aspx";
                        }
                    }
                });
        }
    });
});