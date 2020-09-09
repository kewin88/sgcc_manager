package cn.com.sgcc.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.*;
import org.springframework.context.support.*;

public class Main
{
	private static Log logger = LogFactory.getLog(LoginPanel.class);	
	public static void main(String[] args) throws Exception
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		//MainFrame mainFrame = (MainFrame) ctx.getBean("MainFrameBean");
		LoginFrame mainFrame = (LoginFrame) ctx.getBean("LoginFrameBean");
		mainFrame.init();
		logger.info("加密客户端启动成功");
	}
}
