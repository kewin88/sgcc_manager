<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>批量加密</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="Styles/skins/Aqua/css/ligerui-all.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript" src="js/ligerResizable.js"></script>
<script type="text/javascript" src="js/ligerGrid.js"></script>
<script type="text/javascript" src="js/ligerToolBar.js"></script>
<script type="text/javascript" src="js/ligerui.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript">
	var rootpath = "Styles/skins/Aqua/icons/";
	$(function() {
	});

	$(document).ready(function() {

	});
</script>
<style>
</style>
<%@ page import="java.io.*,java.util.*"%>
<%@ page
	import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<%
	String logStr = "";
	Log logger = LogFactory.getLog("日志管理");
	try {
		String path = this.getServletContext().getRealPath("/") + "logs" + File.separator + "log";
		File file=new File(path); 
		if(!file.exists()||file.isDirectory()){
			throw new FileNotFoundException();
		}
		BufferedReader br=new BufferedReader(new FileReader(file));
		String temp=null;
		StringBuffer sb=new StringBuffer();
		temp = br.readLine();
		while(temp!=null){
			sb.append(temp+"\n");
			temp=br.readLine();
		}
		br.close();
		logStr = sb.toString();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		logger.error("日志文件未找到");
		e.printStackTrace();
		logStr = "日志文件未找到";
	}		
%>
</head>
<body style="padding: 6px; overflow: hidden">
	<div id="logPanel"
		style="float: left; margin-left: 10px; width: 700px; height: 500px"
		class="l-panel">
		<div class="l-panel-header">
			<div style="padding-top: 5px; padding-left: 5px">
				<span>日志</span>
			</div>
		</div>
		<div class="l-panel-content">
			<textarea style="width: 100%; height: 90%; border: 1px; outline: none;"><%=logStr %>
			</textarea>
		</div>
	</div>
</body>
</html>