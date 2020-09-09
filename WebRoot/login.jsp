<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
	
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="Styles/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css" />
    <script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="js/jquery-ui-1.8.21.custom.min.js" type="text/javascript"></script>


<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript" src="js/ligerui.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
		<title>国家电网加密管理系统 </title>
		<script type="text/javascript">
		$(function () {
		    $("#loginDialog").dialog({ minHeight: 200, minWidth: 350 });
		    $("#login").click(function () {
		        $("#message").text("");
		        if ($("#username").val() == "" || $("#password").val() == "") {
		            $("#message").text("用户名或密码不能为空！");
		        }
		        else {
		        	
		        	var name = $("#username").val();
		        	var password = $("#password").val();
		        	$.ajax({
						type : 'POST',
						url : "login!logIn.action",
						data : {
							"name" : name,
							"password" : password
						},
						datatype : "json",
						cache : false,
						complete : function() {
							common.loading = false;
							common.hideLoading();
						},
		        	success: function (json1) {
		        		data = JSON2.parse(json1);
		        		if(data.msg == "SUCCESS"){
		        			location.href = "index.jsp";
		        		}else{
		        			$("#message").text("用户名或密码不正确！");
	                        $("#username").attr("value", "");
	                        $("#password").attr("value", "");
	                        $("#username").focus();
		        		}
                }
            }); 
		        }
		    });
		});
		</script>
		
	</head>
	
	<body>
	
    </div>
		<div id="loginDialog" title="登录">
			<div  theme="simple">
				<table border="0" cellpadding="0" cellspacing="0" align="center">
					<tr>
						<td>用户名：</td>
						<td><input type="text" id="username"/></td>
					</tr>
					<tr>
						<td>密码：</td>
						<td><input type="password"  id="password"/></td>
					</tr>
					<tr>
						<td colspan="80">
						<div style="margin-top:10px">
							<label id = "message" ></label>
						</div>
						</td>
					</tr>
					<tr>
						<td colspan="80">
						<div style="margin-left:100px;margin-top:20px">
							<button type="button" id="login" value="登录">登录</button>
						</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>