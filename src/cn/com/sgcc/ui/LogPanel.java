package cn.com.sgcc.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogPanel extends JPanel {
	
	private static Log logger = LogFactory.getLog(UserManagePanel.class);
	private JTextArea textarea = new JTextArea(5,10);
	private JScrollPane scrollPane;
	
	public LogPanel() {
		super();
	}
	
	public void init() {
		
		setSize(550, 350);
		setLayout(null);
		BoundsHelper.setBounds(textarea, 20, 20, 680, 400);
		add(textarea);
		scrollPane = new JScrollPane(textarea);
		//scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		
		
		BoundsHelper.setBounds(scrollPane, 20, 20, 680, 400);
		textarea.setWrapStyleWord(true);
		textarea.setLineWrap(true);
		
		try {
			loadLogs();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("日志文件未找到");
			e.printStackTrace();
		}
	}
	
	private void loadLogs() throws IOException{
		File file=new File("./logs/log");
		if(!file.exists()||file.isDirectory()){
			System.out.println("ok");
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
		textarea.setText(sb.toString());
	}
}
