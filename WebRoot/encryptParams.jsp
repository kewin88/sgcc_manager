<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>加密参数</title>
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

	var table = null;
	var data = null;

	function listToolbar() {
		var items = [];
		items.push({
			text : '增加',
			click : grid_add,
			img : rootpath + "add.gif"
		});
		items.push({
			text : '修改',
			click : grid_edit,
			img : rootpath + "edit.gif"
		});
		items.push({
			text : '删除',
			click : grid_delete,
			img : rootpath + "delete.gif"
		});
		return {
			items : items
		};
	}
	var isAdd;
	//add function
	function grid_add() {
		isAdd = true;
		var row = {
			beginIp : null,
			endIp : null
		};
		showDetail(row);
	}
	//edit function
	function grid_edit() {
		isAdd = false;
		var row = table.getSelectedRow();
		if (!row) {
			$.ligerDialog.warn('请选择要操作的行！')
			return;
		}
		showDetail(row);
	}
	//delete function
	function grid_delete() {
		var row = table.getSelectedRow();
		if (!row) {
			$.ligerDialog.warn('请选择要操作的行！')
			return;
		}
		$.ajax({
			type : 'POST',
			url : "ipRange!ipRangeDeleteMET.action",
			data : {
				"id" : row.id
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
				refreshTable();
				common.tip('删除成功！');
			}
		});
	}

	function showDetail(row, action) {
		var ipForm = $("#ipForm").ligerForm({
			inputWidth : 200,
			labelWidth : 90,
			space : 20,
			fields : [ {
				display : "起始IP",
				name : "beginIp",
				newline : true,
				type : "text",
				validate : {
					required : true
				}
			}, {
				display : "结束IP",
				name : "endIp",
				newline : true,
				type : "text",
				validate : {
					required : true
				}
			} ]
		});
		var title1;
		if (isAdd) {
			title1 = '增加IP';
		} else {
			title1 = '修改IP';
		}
		common.loadForm(ipForm, row);
		$.ligerDialog.open({
			title : title1,
			target : $("#ipForm"),
			width : 400,
			height : 120,
			isResize : false,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					var beginIp = $("#beginIp").val();
					var endIp = $("#endIp").val();
					if (isAdd) {
						$.ajax({
							type : 'POST',
							url : "ipRange!ipRangeAddMET.action",
							data : {
								"beginIp" : beginIp,
								"endIp" : endIp
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
								refreshTable();
								common.tip('录入成功！');
							}
						});
					} else {
						$.ajax({
							type : 'POST',
							url : "ipRange!ipRangeEditMET.action",
							data : {
								"id" : row.id,
								"beginIp" : beginIp,
								"endIp" : endIp
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
								refreshTable();
								common.tip('修改成功！');
							}
						});
					}
					dialog.hide();
				}
			}, {
				text : '关闭',
				onclick : function(item, dialog) {
					dialog.hide();
				}
			} ]
		});

	}
	//search function
	function f_search() {
		table.options.data = $.extend(true, {}, data);
		table.loadData(f_getWhere());
	}
	function f_getWhere() {
		if (!table)
			return null;
		var clause = function(rowdata, rowindex) {
			var key = $("#txtbeginIp").val();
			return rowdata.beginIp.indexOf(key) > -1;
		};
		return clause;
	}
	$(function() {
		refreshTable();
		refreshRights();
	});
	// refresh table function
	function refreshTable() {
		$.ajax({
			type : 'POST',
			url : "ipRange!ipRangeListMET.action",
			data : {},
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
				data = JSON2.parse(json1);
				table = $("#table").ligerGrid({
					columns : [ {
						display : '起始IP',
						name : 'beginIp',
						width : '50%'
					}, {
						display : '结束IP',
						name : 'endIp',
						width : '50%'
					} ],
					toolbar : listToolbar(),
					pageSize : 10,
					checkbox : false,
					rownumbers : false,
					where : f_getWhere(),
					data : $.extend(true, {}, data),
					width : '68%',
					height : '90%'
				});
			}
		});
	}
	function refreshRights(){
		$.ajax({
			type : 'POST',
			url : "rights!rightsListMET.action",
			data : {},
			datatype : "json",
			cache : false,
			complete : function() {
				common.loading = false;
				common.hideLoading();
			},
			success : function(json1) {
				data = JSON2.parse(json1);
				var rights = data.rights;
				 $("#onlineDisplayCheckBox").attr("checked", rights.onlineDisplayable);
				 $("#onlinePrintCheckBox").attr("checked", rights.onlinePrintable);
				 $("#onlineCopyCheckBox").attr("checked", rights.onlineCopyable);
				 $("#offlineDisplayCheckBox").attr("checked", rights.offlineDisplayable);
				 if(rights.offlineDisplayable){
					 $("#offlineDisplayDurationTextField").val(rights.offlineDisplayDuration);
					 $("#offlineDisplayCountTextField").val(rights.offlineDisplayCount);
				 }else{
					 $("#offlineDisplayDurationTextField").attr("disabled", rights.offlineDisplayDuration);
					 $("#offlineDisplayCountTextField").attr("disabled", rights.offlineDisplayCount);
				 }
				console.log(data.rights);
			}
		});
	}
	function rights(){
		var onlineDisplayable = $("#onlineDisplayCheckBox").attr('checked');
		var onlinePrintable = $("#onlinePrintCheckBox").attr('checked');
		var onlineCopyable = $("#onlineCopyCheckBox").attr('checked');
		var offlineDisplayable = $("#offlineDisplayCheckBox").attr('checked');
		var offlineDisplayDuration = $("#offlineDisplayDurationTextField").val();
		var offlineDisplayCount = $("#offlineDisplayCountTextField").val();
		$.ajax({
			type : 'POST',
			url : "rights!rightsAddMET.action",
			data : {
				onlineDisplayable: onlineDisplayable,
				onlinePrintable: onlinePrintable,
				onlineCopyable: onlineCopyable,
				offlineDisplayable: offlineDisplayable,
				offlineDisplayDuration: offlineDisplayDuration,
				offlineDisplayCount: offlineDisplayCount
			},
			datatype : "json",
			cache : false,
			complete : function() {
				common.loading = false;
				common.hideLoading();
			},
			success : function() {
				common.tip('修改成功！');
			}
		});
	}
	function offlineDisplay(){
		if($("#offlineDisplayCheckBox").attr('checked')){
			 $("#offlineDisplayDurationTextField").attr("disabled", false);
			 $("#offlineDisplayCountTextField").attr("disabled", false);
		}else{
			 $("#offlineDisplayDurationTextField").val('');
			 $("#offlineDisplayCountTextField").val('');
			$("#offlineDisplayDurationTextField").attr("disabled", true);
			 $("#offlineDisplayCountTextField").attr("disabled", true);
		}
	}
</script>
<style>
.my-button {
	background: #E0EDFF url(../images/controls/button-bg.gif) repeat-x
		center;
	cursor: pointer;
	border: solid 1px #A3C0E8
}

.rightsPanel {
	width: 66%;
	padding: 10px;
	border: solid 1px #A3C0E8
}

.checkBoxItem {
	width: 220px;
	display: inline-block;
}
</style>
</head>
<body style="padding: 6px; overflow: hidden">
	<div id="rightsPanel" class="rightsPanel">
		<h3 style="margin-bottom: 10px;">权限</h3>
		<div class="checkBoxItem">
			<label><input id="onlineDisplayCheckBox" type="checkbox"
				value="true" />在线打开</label>
		</div>
		<div class="checkBoxItem">
			<label><input id="onlinePrintCheckBox" type="checkbox"
				value="true" />在线打印</label>
		</div>
		<div class="checkBoxItem">
			<label><input id="onlineCopyCheckBox" type="checkbox"
				value="true" />在线复制</label>
		</div>
		<br>
		<div class="checkBoxItem">
			<label><input id="offlineDisplayCheckBox" type="checkbox"
				value="true" onchange="offlineDisplay()" />借阅打开</label>
		</div>
		<div class="checkBoxItem">
			<label>借阅打开时间</label> <input id="offlineDisplayDurationTextField"
				type="text" class="l-text" style="width: 80px;">
		</div>
		<div class="checkBoxItem">
			<label>借阅打开次数</label> <input id="offlineDisplayCountTextField"
				type="text" class="l-text" style="width: 80px;">
			<button class="my-button" id="btnSearch" onclick="rights()"
				style="dispaly: inline-block;margin-left:20px">确定</button>
		</div>


	</div>
	<div id="searchbar" style="margin: 20px 0 5px 10px">
		<label style="width: 65px; display: inline-block">起始IP：</label> <input
			id="txtbeginIp" type="text" class="l-text">
		<button class="my-button" id="btnSearch" onclick="f_search()"
			style="dispaly: inline">搜索</button>
	</div>
	<div id="table"></div>
	<div id="ipForm"></div>
</body>
</html>