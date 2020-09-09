package cn.com.sgcc.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import org.apache.commons.logging.*;
import org.springframework.beans.factory.DisposableBean;

import cn.com.sgcc.db.*;
import cn.com.sgcc.dao.*;
import cn.com.sgcc.vo.*;

public class LoginPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	public static final int EDIT_STATE_NONE   = 0;
	public static final int EDIT_STATE_ADD    = 1;
	public static final int EDIT_STATE_UPDATE = 2;

	private static Log logger = LogFactory.getLog(LoginPanel.class);

	private DatabaseLayer databaseLayer;
	private MainFrame mainFrame;
	private JList list = new JList();
	private JScrollPane scroll = new JScrollPane(list);
	private JButton loginButton;
	private JLabel usernameLabel;
	private JTextField usernameTextField;
	private JLabel passwordLabel;
	private JPasswordField passwordTextField;

	public void setDatabaseLayer(DatabaseLayer databaseLayer)
	{
		this.databaseLayer = databaseLayer;
	}
    
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	
	public LoginPanel()
	{
		super();
		//System.out.println("panel start");
		//setSize(550, 350);
		setLayout(null);
		setBounds(0, 0, 512, 303);
		setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		
		usernameLabel = new JLabel("用户名");
		usernameLabel.setBounds(154, 73, 42, 18);
		usernameLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		
		passwordLabel = new JLabel("密    码");
		passwordLabel.setBounds(154, 148, 42, 18);
		passwordLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(230, 71, 132, 23);
		usernameTextField.setColumns(10);
		
		passwordTextField =  new JPasswordField();
		passwordTextField.setBounds(230, 143, 132, 23);
		passwordTextField.setColumns(10);
		
		loginButton = new JButton("登    录");
		loginButton.setBounds(213, 223, 73, 22);

		add(scroll);
		add(loginButton);
		add(usernameLabel);
		add(usernameTextField);
		add(passwordLabel);
		add(passwordTextField);

		//BoundsHelper.setBounds(scroll, 20, 20, 300, 280);
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new IpRangeListCellRenderer());

		loginButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
	
				String u = usernameTextField.getText().trim();
				String p = passwordTextField.getText().trim();
				try {
					User user = databaseLayer.getUserDao().select(u);
					if (user!=null  && user.getPassword().equals(p)){
						getParent().getParent().getParent().getParent().setVisible(false);
						logger.info(u + "登录成功！");
						mainFrame.init();
					} else {
						JOptionPane.showMessageDialog(null, "登录失败，用户名或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
					}
				} catch (DaoException e) {
					logger.error("", e);
					JOptionPane.showMessageDialog(null, "致命错误，请查看日志！", "错误", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});

		
	}

	public void init()
	{
		refreshList();
	}

	private void refreshList()
	{
		try
		{
			Object[] ips = databaseLayer.getIpRangeDao().selectAll().toArray();
			list.setListData(ips);
			list.setSelectedIndex(0);
		}
		catch (DaoException e)
		{
			logger.error("", e);
			JOptionPane.showMessageDialog(null, "致命错误，请查看日志！", "错误", JOptionPane.ERROR_MESSAGE);
		}
	}


	public boolean isIp(String IP)
	{
		boolean b = false;
		if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"))
		{
			String s[] = IP.split("\\.");
			if ((Integer.parseInt(s[0]) <= 255) && (Integer.parseInt(s[1]) <= 255) && 
						(Integer.parseInt(s[2]) <= 255) && (Integer.parseInt(s[3]) <= 255))
			{
				b = true;
			}
		}
		return b;
	}

}
