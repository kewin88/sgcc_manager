package cn.com.sgcc.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import org.apache.commons.logging.*;

import cn.com.sgcc.db.*;
import cn.com.sgcc.dao.*;
import cn.com.sgcc.vo.*;

public class UserManagePanel extends JPanel
{
	public static final int EDIT_STATE_NONE   = 0;
	public static final int EDIT_STATE_ADD    = 1;
	public static final int EDIT_STATE_UPDATE = 2;

	private static Log logger = LogFactory.getLog(UserManagePanel.class);

	private DatabaseLayer databaseLayer = new DatabaseLayer();

	private JList list = new JList();
	private JScrollPane scroll;
	private JButton addButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JLabel userNameLabel;
	private JTextField usernameTextField;
	private JLabel passwordLabel;
	private JTextField passwordTextField;
	private JButton confirmButton;

	private int editState = EDIT_STATE_NONE;
    
	public UserManagePanel()
	{
		super();
	}

	public void init()
	{
		setSize(550, 350);
		setLayout(null);
		
		scroll = new JScrollPane(list);
		scroll.setBounds(22, 26, 359, 372);
		add(scroll);
		
		addButton = new JButton("新  增");
		addButton.setBounds(422, 33, 93, 22);
		add(addButton);
		
		updateButton = new JButton("修  改");
		updateButton.setBounds(422, 88, 93, 23);
		add(updateButton);
		
		deleteButton = new JButton("删  除");
		deleteButton.setBounds(422, 142, 93, 22);
		add(deleteButton);
		
		JPanel userUpdatePanel = new JPanel();
		userUpdatePanel.setBorder(new EtchedBorder());
		userUpdatePanel.setBounds( 422, 200, 244, 198);
		add(userUpdatePanel);
		userUpdatePanel.setLayout(null);
		
		userNameLabel = new JLabel("用户名");
		userNameLabel.setBounds(25, 16, 54, 15);
		userUpdatePanel.add(userNameLabel);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(25, 45, 186, 21);
		userUpdatePanel.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		passwordLabel = new JLabel("密  码");
		passwordLabel.setBounds(25, 84, 54, 15);
		userUpdatePanel.add(passwordLabel);
		
		passwordTextField = new JTextField();
		passwordTextField.setBounds(25, 112, 186, 21);
		userUpdatePanel.add(passwordTextField);
		passwordTextField.setColumns(10);
		
		confirmButton = new JButton("确  认");
		confirmButton.setBounds(73, 154, 93, 23);
		userUpdatePanel.add(confirmButton);

		setEditAreaEnabled(false);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new UserListCellRenderer());

		list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent lse)
			{
				if (null != list.getSelectedValue())
				{
					User ip = (User) list.getSelectedValue();
					setEditAreaEnabled(false);
					usernameTextField.setText(ip.getName());
					passwordTextField.setText(ip.getPassword());
				}
			}
		});

		addButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				setEditAreaEnabled(true);
				usernameTextField.setText("");
				passwordTextField.setText("");
				editState = EDIT_STATE_ADD;
			}
		});

		updateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				setEditAreaEnabled(true);
				editState = EDIT_STATE_UPDATE;
			}
		});

		deleteButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if (null != list.getSelectedValue())
				{
					User user = (User) list.getSelectedValue();
					try
					{
						databaseLayer.getUserDao().delete(user.getId());
					}
					catch (DaoException e)
					{
						logger.error("", e);
						JOptionPane.showMessageDialog(null, "致命错误，请查看日志！", "错误", JOptionPane.ERROR_MESSAGE);
					}
					refreshList();
				}
			}
		});

		confirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String u = usernameTextField.getText().trim();
				String p = passwordTextField.getText().trim();

				if (EDIT_STATE_ADD == editState)
				{
					User user = new User();
					user.setName(u);
					user.setPassword(p);
					try
					{
						databaseLayer.getUserDao().insert(user);
					}
					catch (DaoException e)
					{
						logger.error("", e);
						JOptionPane.showMessageDialog(null, "致命错误，请查看日志！", "错误", JOptionPane.ERROR_MESSAGE);
					}
					refreshList();
				}
				else if (EDIT_STATE_UPDATE == editState)
				{
					User user = new User();
					user.setName(u);
					user.setPassword(p);
					try
					{
						databaseLayer.getUserDao().update(user);
					}
					catch (DaoException e)
					{
						logger.error("", e);
						JOptionPane.showMessageDialog(null, "致命错误，请查看日志！", "错误", JOptionPane.ERROR_MESSAGE);
					}
					refreshList();
				}
			}
		});
		
		
		
		refreshList();
	}

	public void refreshList()
	{
		try
		{
			Object[] users = databaseLayer.getUserDao().selectAll().toArray();
			list.setListData(users);
			list.setSelectedIndex(0);
			System.out.println(users);
		}
		catch (DaoException e)
		{
			logger.error("", e);
			JOptionPane.showMessageDialog(null, "致命错误，请查看日志！", "错误", JOptionPane.ERROR_MESSAGE);
		}
		editState = EDIT_STATE_NONE;
	}

	private void setEditAreaEnabled(boolean b)
	{
		usernameTextField.setEditable(b);
		passwordTextField.setEditable(b);
		confirmButton.setEnabled(b);
	}


}
