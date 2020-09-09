<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>文件传输</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="Styles/skins/Aqua/css/ligerui-all.css" rel="stylesheet"
	type="text/css"/>
<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript" src="js/ligerResizable.js"></script>
<script type="text/javascript" src="js/ligerGrid.js"></script>
<script type="text/javascript" src="js/ligerui.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript">
	var rootpath = "Styles/skins/Aqua/icons/";
	var data;
	var fileTable = null;
	$(function() {
		refreshTable();
	});

	$(document).ready(function() {
		$("#bb").click(function() {
			var from = $("#from").val();
			var to = $("#to").val();
			if (from == '') {
				alert("请选择源路径!");
				return;
			}
			;
			if (to == '') {
				alert("请选择目标路径!");
				return;
			}
			;

			$.ajax({
				type : 'POST',
				url : "ribao!ribaoMET.action",
				data : {
					"cong" : cong,
					"dao" : dao
				},
				datatype : "json",
				cache : false,
				beforeSend : function() {
					common.loading = true;
					common.showLoading("数据获取中...");
				},
				complete : function() {
					common.loading = false;
					common.hideLoading();
				},
				success : function(json1) {
					var json = JSON2.parse(json1);
				}
			})
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
			url : "transfer!transferListMET.action",
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
					columns : [ 
					{
						display : '序号',
						name : 'order',
						width : '20%'
					}, 
								            {
						display : '文件',
						name : 'fileName',
						width : '40%'
					}, {
						display : '文件路径',
						name : 'fileDir',
						width : '40%'
					}],
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
	function download(){
			var row = fileTable.getSelectedRow();
			if (!row) {
				$.ligerDialog.warn('请选择要操作的行！')
				return;
			}
<%-- 			alert("<%=path%>/servlet/DownloadServlet"); --%>
			 document.getElementById("fileName").value = row.fileName; 
			 document.getElementById("fileDir").value = row.fileDir; 
			 document.fileForm.action = "<%=path%>/servlet/DownloadServlet"; 
			 document.fileForm.submit();
	<%-- 		 $.ajax({
					type : 'POST',
					url : "<%=path%>/servlet/DownloadServlet",
					data : {
						"fileName" : row.fileName,
						"fileDir" : row.fileDir
					},
					datatype : "json",
					cache : false,
					beforeSend : function() {
					},
					complete : function() {
						common.loading = false;
						common.hideLoading();
					},
					success : function(json1) {
						console.log(json1);
					}
				}) --%>
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
	</div>
	<div id="searchbar" style="margin: 20px 0 5px 10px">
		<label style="width: 65px; display: inline-block">文件名：</label> 
		<input id="txtFileName" type="text" class="l-text">
		<button class="my-button" id="btnSearch" onclick="f_search()" style="dispaly:inline">搜索</button>
		<button class="my-button" id="btnDownload" onclick="download()" style="dispaly:inline;margin-left:10px">下载</button>
		<label id="info" style="margin-left:100px"></label>
	</div>
	<div id="fileTable"></div>
	<form name="fileForm">
		  <input type="hidden" name="fileName" id="fileName">
		  <input type="hidden" name="fileDir" id="fileDir">
	</form>
</body>
</html>