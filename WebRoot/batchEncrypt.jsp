<%@ page language="java" import="java.util.*"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>批量加密</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="Styles/skins/Aqua/css/ligerui-all.css" rel="stylesheet"
	type="text/css"/>
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
	var fileTable = null;
	var data = null;
	
	$(function() {
		refreshTable();
	});

	$(document).ready(function() {
		$("#bb").click(function() {
		});
	});
	//search function
	function f_search() {
		fileTable.options.data = $.extend(true, {}, data);
		fileTable.loadData(f_getWhere());
	}
	function f_getWhere() {
		if (!fileTable)
			return null;
		var clause = function(rowdata, rowindex) {
			var key = $("#txtFileName").val();
			return rowdata.fileName.indexOf(key) > -1;
		};
		return clause;
	}
	function refreshTable(){
		
		$.ajax({
			type : 'POST',
			url : "encryption!encryptionListMET.action",
			data : {},
			datatype : "json",
			cache : false,
			beforeSend : function() {
				
			},
			complete : function() {
				common.loading = false;
				common.hideLoading();
			},
			success : function(json1) {
				data = JSON2.parse(json1);
				fileTable = $("#fileTable").ligerGrid({
					columns : [{
						display : '序号',
						name : 'order',
						width : '10%'
					}, 
					{
						display : '文件名',
						name : 'fileName',
						width : '20%'
					}, 
					{
						display : '文件路径',
						name : 'fileDir',
						width : '40%'
					}, 
					{
						display : '待加密',
						name : 'isNotEncrypted',
						width : '10%'
					}, {
						display : '成功',
						name : 'isSuccessed',
						width : '10%'
					}, {
						display : '失败',
						name : 'isFailed',
						width : '10%'
					} ],
					pageSize : 10,
					checkbox : false,
					rownumbers : false,
					where : f_getWhere(),
					data : $.extend(true, {}, data),
					width : '98%',
					height : '90%'
				});
				var info = "文件总个数：" + data.Count;
				$("#info").text(info);
			}
		});
		
	}
	function encrypt(){
		$.ajax({
			type : 'POST',
			url : "encryption!encryptionMET.action",
			data : {},
			datatype : "json",
			cache : false,
			beforeSend : function() {
				
			},
			complete : function() {
				common.loading = false;
				common.hideLoading();
			},
			success : function(json1) {
				data = JSON2.parse(json1);
				fileTable = $("#fileTable").ligerGrid({
					columns : [{
						display : '序号',
						name : 'order',
						width : '10%'
					}, 
					{
						display : '文件名',
						name : 'fileName',
						width : '20%'
					}, 
					{
						display : '文件路径',
						name : 'fileDir',
						width : '40%'
					}, 
					{
						display : '待加密',
						name : 'isNotEncrypted',
						width : '10%'
					}, {
						display : '成功',
						name : 'isSuccessed',
						width : '10%'
					}, {
						display : '失败',
						name : 'isFailed',
						width : '10%'
					}],
					pageSize : 10,
					checkbox : false,
					rownumbers : false,
					where : f_getWhere(),
					data : $.extend(true, {}, data),
					width : '98%',
					height : '90%'
				});
				$("#info").text(data.Info);
			}
		});
	}
	
</script>
<style>
.my-button{
	background:#E0EDFF url(../images/controls/button-bg.gif) repeat-x center;
	cursor:pointer;
	border:solid 1px #A3C0E8
}
</style>
</head>
<body style="padding: 6px; overflow: hidden">
	<div style="margin:10px 0 0 10px">
	<form action="<%=path%>/servlet/NormalFileUploadServlet" enctype="multipart/form-data"  method="post">
          <p style="display:inline-block">上传文件：<input type="file" name="file"/> </p>
         <input type="submit" value="上传" style="width:50"/><span>（支持pdf）</span>
    </form>
  <%--   <form action="<%=path%>/servlet/NormalFileUploadServlet"  enctype="multipart/form-data"   method="post">
		  <input type="text" value="test123" name="username"/>
          <p>上传文件：<input type="file" name="file"/> </p>
         <input type="submit" value="上传" style="width:50"/>
    </form> --%>
<!-- 		<div>
			<label style="width: 65px; display: inline-block">源路径:</label>
			<input type="text" id="from" placeholder="from" style="width: 400px;" class="l-text">
			<button onclick="getFiles()" class="my-button" style="margin:0px 0px 0px 15px;">确定</button>
		</div>
		<div style="margin:10px 0 0 0">
			<label style="width: 65px; display: inline-block">目标路径:</label>
			<input type="text" id="to" placeholder="to" style="width: 400px;" class="l-text">
			<button on-click="encrypt()" class="my-button" style="margin:0px 0px 0px 15px;">开始加密</button>
		</div> -->
	</div>
	<div id="searchbar" style="margin: 20px 0 5px 10px">
		<label style="width: 65px; display: inline-block">文件名：</label> 
		<input id="txtFileName" type="text" class="l-text">
		<button class="my-button"  onclick="f_search()" style="dispaly:inline">搜索</button>
		<button onclick="encrypt()" class="my-button" style="margin:0px 0px 0px 15px;">开始加密</button>
		<button onclick="refreshTable()" class="my-button" style="margin:0px 0px 0px 5px;">刷新</button>
		<label id="info" style="margin-left:40px"></label>
	</div>
	<div id="fileTable"></div>
</body>
</html>