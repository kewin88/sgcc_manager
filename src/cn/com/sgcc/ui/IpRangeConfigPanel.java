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

public class IpRangeConfigPanel extends JPanel
{
	public static final int EDIT_STATE_NONE   = 0;
	public static final int EDIT_STATE_ADD    = 1;
	public static final int EDIT_STATE_UPDATE = 2;

	private static Log logger = LogFactory.getLog(IpRangeConfigPanel.class);

	private DatabaseLayer databaseLayer;

	private JList list;
	private JScrollPane scroll;
	private JButton addButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JPanel ipPanel;
	private JLabel beginIpLabel;
	private JTextField beginIpTextField;
	private JLabel endIpLabel;
	private JTextField endIpTextField;
	private JButton confirmButton;
	private RightsPanel rightsPanel;

	private int editState = EDIT_STATE_NONE;

	public void setDatabaseLayer(DatabaseLayer databaseLayer)
	{
		this.databaseLayer = databaseLayer;
	}
    
	public IpRangeConfigPanel()
	{
		super();
//		initialize();
	}
	
	public void initialize()
	{
		
		setLayout(null);
		
		rightsPanel=new RightsPanel();
		add(rightsPanel);
		setDefaultRights();
		
        list = new JList();
		
		scroll = new JScrollPane(list);
		scroll.setBounds(22, 200, 359, 161);
		add(scroll);
		
		addButton = new JButton("新  增");
		addButton.setBounds(22, 376, 93, 22);
		add(addButton);
		
		updateButton = new JButton("修  改");
		updateButton.setBounds(158, 376, 93, 23);
		add(updateButton);
		
		deleteButton = new JButton("删  除");
		deleteButton.setBounds(288, 376, 93, 22);
		add(deleteButton);
		
		ipPanel = new JPanel();
		ipPanel.setBorder(new EtchedBorder());
		ipPanel.setBounds( 422, 200, 244, 198);
		add(ipPanel);
		ipPanel.setLayout(null);
		
		beginIpLabel = new JLabel("起始IP地址");
		beginIpLabel.setBounds(25, 16, 85, 15);
		ipPanel.add(beginIpLabel);
		
		beginIpTextField = new JTextField();
		beginIpTextField.setBounds(25, 45, 186, 21);
		ipPanel.add(beginIpTextField);
		beginIpTextField.setColumns(10);
		
		endIpLabel = new JLabel("结束IP地址");
		endIpLabel.setBounds(25, 84, 85, 15);
		ipPanel.add(endIpLabel);
		
		endIpTextField = new JTextField();
		endIpTextField.setBounds(25, 112, 186, 21);
		ipPanel.add(endIpTextField);
		endIpTextField.setColumns(10);
		
		confirmButton = new JButton("确  认");
		confirmButton.setBounds(73, 154, 93, 23);
		ipPanel.add(confirmButton);

		setEditAreaEnabled(false);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new IpRangeListCellRenderer());

		list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent lse)
			{
				if (null != list.getSelectedValue())
				{
					IpRange ip = (IpRange) list.getSelectedValue();
					setEditAreaEnabled(false);
					beginIpTextField.setText(ip.getBeginIp());
					endIpTextField.setText(ip.getEndIp());
				}
			}
		});

		addButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				setEditAreaEnabled(true);
				beginIpTextField.setText("");
				endIpTextField.setText("");
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
					IpRange ip = (IpRange) list.getSelectedValue();
					try
					{
						databaseLayer.getIpRangeDao().delete(ip.getId());
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
				String beginIp = beginIpTextField.getText().trim();
				String endIp = endIpTextField.getText().trim();
				if (!isIp(beginIp))
				{
					JOptionPane.showMessageDialog(null, "请输入正确的起始IP地址！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!isIp(endIp))
				{
					JOptionPane.showMessageDialog(null, "请输入正确的结束IP地址！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (EDIT_STATE_ADD == editState)
				{
					IpRange ip = new IpRange();
					ip.setBeginIp(beginIp);
					ip.setEndIp(endIp);
					try
					{
						databaseLayer.getIpRangeDao().insert(ip);
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
					IpRange ip = (IpRange) list.getSelectedValue();
					ip.setBeginIp(beginIp);
					ip.setEndIp(endIp);
					try
					{
						databaseLayer.getIpRangeDao().update(ip);
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
	
	public Rights getRights()
	{
		return rightsPanel.getRights();
	}
	private void setDefaultRights()
	{
		Rights rights = new Rights();
		rights.setOnlineDisplayable(true);
		rightsPanel.setRights(rights);
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
		editState = EDIT_STATE_NONE;
	}

	private void setEditAreaEnabled(boolean b)
	{
		beginIpTextField.setEditable(b);
		endIpTextField.setEditable(b);
		confirmButton.setEnabled(b);
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
