package cn.com.sgcc.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.security.*;
import java.io.*;

import org.apache.commons.logging.*;

import cn.com.sgcc.db.*;
import cn.com.sgcc.dao.*;
import cn.com.sgcc.vo.*;
import cn.com.sgcc.generic.*;

public class RightsConfigPanel extends JPanel
{
	private static Log logger = LogFactory.getLog(RightsConfigPanel.class);

	private DatabaseLayer databaseLayer;

	private JLabel filenameLabel = new JLabel("输入要设置权限的文件名:");
	private JTextField filenameTextField = new JTextField();
	private JButton displayRightsButton = new JButton("显示权限");
	private RightsPanel rightsPanel = new RightsPanel();
	private JButton updateRightsButton = new JButton("更新权限");

	private Resource resource = null;

	public void setDatabaseLayer(DatabaseLayer databaseLayer)
	{
		this.databaseLayer = databaseLayer;
	}
    
	public RightsConfigPanel()
	{
		super();

		setSize(550, 350);
		setLayout(null);
		add(filenameLabel);
		add(filenameTextField);
		add(displayRightsButton);
		add(rightsPanel);
		add(updateRightsButton);

		BoundsHelper.setBounds(filenameLabel, 20, 20);
		BoundsHelper.setBounds(filenameTextField, 20, 40, 390, 30);
		BoundsHelper.setBounds(displayRightsButton, 420, 40, 100, 30);
		rightsPanel.setLocation(20, 90);
		BoundsHelper.setBounds(updateRightsButton, 220, 260, 100, 30);

		rightsPanel.setEnabled(false);
		updateRightsButton.setEnabled(false);

		displayRightsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String filename = filenameTextField.getText().trim();
				try
				{
					MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
					String filenameHash = Helper.byteArrayToHexString(sha1.digest(filename.getBytes("utf-8")));
					resource = databaseLayer.getResourceDao().select(filenameHash);
				}
				catch (DaoException e)
				{
					logger.error("", e);
					JOptionPane.showMessageDialog(null, "致命错误，请查看日志！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				catch (NoSuchAlgorithmException e)
				{
					logger.error("", e);
					JOptionPane.showMessageDialog(null, "致命错误，请查看日志！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				catch (UnsupportedEncodingException e)
				{
					logger.error("", e);
					JOptionPane.showMessageDialog(null, "致命错误，请查看日志！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (null == resource)
				{
					JOptionPane.showMessageDialog(null, "找不到该文件，请输入有效文件名！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				rightsPanel.setRights(resource.getRights());
				rightsPanel.setEnabled(true);
				updateRightsButton.setEnabled(true);
			}
		});

		updateRightsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if (null == resource)
				{
					return;
				}
				try
				{
					Rights rights = rightsPanel.getRights();
					resource.setRights(rights);
					databaseLayer.getResourceDao().update(resource);
					JOptionPane.showMessageDialog(null, "权限更新成功！", "信息", JOptionPane.INFORMATION_MESSAGE);
				}
				catch (DaoException e)
				{
					logger.error("", e);
					JOptionPane.showMessageDialog(null, "致命错误，请查看日志！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				catch (NumberFormatException e)
				{
					JOptionPane.showMessageDialog(null, "借阅小时数和次数必须是正整数！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
	}

	public void init()
	{
	}
}
