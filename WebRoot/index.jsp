<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>国家电网加密管理系统</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
    <link href="Styles/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="Styles/default.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery-1.8.1.js"></script>
    <script type="text/javascript" src="js/ligerui.min.js"></script>
    <script type="text/javascript" src="js/default.js"></script>
    <script type="text/javascript" src="js/indexdata.js" ></script>
    <script type="text/javascript">
        function AutoScroll(obj) {
            $(obj).find("ul:first").animate({
                marginTop: "-25px"
            }, 500, function () {
                $(this).css({ marginTop: "0px" }).find("li:first").appendTo(this);
            });
        }
        $(document).ready(function () {
            setInterval('AutoScroll("#scrollDiv")', 1000);
        });  
     </script>
     <script type="text/javascript">
			
			function logOut() {
				window.location.href="logOut.action";
			}
		</script>
    <style type="text/css">
        ul, li
        {
            margin: 0;
            padding: 0;
        }
        #scrollDiv
        {
            width: 430px;
            height: 150px;
            line-height:15px;
            border: #ccc 1px solid;
            overflow: hidden;
        }
        #scrollDiv li
        {
            height: 25px;
            padding-left: 10px;
        }
    </style>

</head>
<body>
    <div id="pageloading">
    </div>
    <form id="Form1" runat="server">
    <div id="topmenu" class="l-topmenu">
        <div class="l-topmenu-logo" style="margin-left:0px">　 　 　 　 　  国家电网加密管理系统</div>
                                           
        <div class="l-topmenu-welcome">
            
         
            <asp:LinkButton ID="LinkButton_exit" runat="server" OnClick="javascript:logOut()">退出</asp:LinkButton>
        </div>
    </div>
    
    
    <div id="layout1" style="width: 99.2%; margin: 0 auto; margin-top: 4px;">
        <div id="accordion1" position="left" 
        title="我的面板">
            <div title="功能列表" class="l-scroll">
                <ul id="tree1">
                </ul>
            </div>
  
        </div>
        <div id="framecenter" position=center>
            <div tabid="home" title="欢迎登录" style="height: 500px">
            <div class="PageMain">
                <div class="title1">公告</div>
                <div id="scrollDiv">
                    <ul>
                        <li>这是滚动公告的第一行</li>
                        <li>这是滚动公告的第二行</li>
                        <li>这是滚动公告的第三行</li>
                        <li>这是滚动公告的第四行</li>
                        <li>这是滚动公告的第五行</li>
                        <li>这是滚动公告的第六行</li>
                        <li>这是滚动公告的第七行</li>
                        <li>这是滚动公告的第八行</li>
                    </ul>
                </div>
            </div>
            </div>
                  </div>
    </div>
    <div class="displaynone">
    </div>
    <div style="height: 32px; line-height: 32px; text-align: center;">
       </div>
    </form>
</body>
</html>
    