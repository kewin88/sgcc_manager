package cn.com.sgcc.newui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.BoxLayout;

import java.awt.GridLayout;
import java.awt.CardLayout;

import javax.swing.JButton;

import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.mysql.jdbc.RowData;

import net.miginfocom.swing.MigLayout;

import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JLayeredPane;
import javax.swing.JToolBar;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;

import java.awt.Component;

import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JProgressBar;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class mainframe
{

	private JFrame frame;
	private JTextField fromTextField;
	private JTextField toTextField;
	private JTable table;
	private DefaultTableModel model;
	private Vector data = new Vector(); //data for the table
	private Vector columnNames=new Vector(Arrays.asList(new String[]{ "文件", "待加密", "成功", "失败"}));
	private JLabel totalLabel;
	private JTextField beginIpTextField;
	private JTextField endIpTextField;
	private JTextField offlineDisplayDurationTextField;
	private JTextField offlineDisplayCountTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					mainframe window = new mainframe();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainframe()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 747, 497);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 731, 458);
		frame.getContentPane().add(tabbedPane);

		JPanel encryptPanel = new JPanel();
		tabbedPane.addTab("加密管理", new ImageIcon("./icon/a.png"), encryptPanel, null);
		encryptPanel.setLayout(null);

		JLabel fromLabel = new JLabel("源路径");
		fromLabel.setBounds(61, 23, 77, 20);
		encryptPanel.add(fromLabel);

		fromTextField = new JTextField();
		fromTextField.setBounds(135, 23, 424, 21);
		encryptPanel.add(fromTextField);
		fromTextField.setColumns(10);

		JLabel toLabel = new JLabel("目的路径\r\n");
		toLabel.setBounds(61, 66, 54, 15);
		encryptPanel.add(toLabel);

		toTextField = new JTextField();
		toTextField.setBounds(135, 63, 424, 21);
		encryptPanel.add(toTextField);
		toTextField.setColumns(10);

		JButton fromButton = new JButton("选择");
		

		fromButton.setBounds(582, 22, 64, 23);
		encryptPanel.add(fromButton);

		JButton toButton = new JButton("选择\r\n");
		toButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser c = new JFileChooser();
				c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				c.setDialogTitle("Select path to save");
				int result = c.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					String path = c.getSelectedFile().getAbsolutePath();
					toTextField.setText(path);
				}
			}
		});
		toButton.setBounds(582, 62, 64, 23);
		encryptPanel.add(toButton);

		
		
		
		
		model=new DefaultTableModel(data,columnNames) //model for the table
		{
			Class[] columnTypes = new Class[] { Object.class, Boolean.class,
					Boolean.class, Boolean.class };

			public Class getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false };

			public boolean isCellEditable(int row, int column)
			{
				return columnEditables[column];
			}
		};
		
		table=new JTable(model);   //create table
		
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
//		table.getColumnModel().getColumn(1).setPreferredWidth(100);
//		table.getColumnModel().getColumn(2).setPreferredWidth(100);
//		table.getColumnModel().getColumn(3).setPreferredWidth(100);

		table.setPreferredScrollableViewportSize(new Dimension(500, 500));
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(61, 106, 585, 228);
		encryptPanel.add(scrollPane);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(61, 355, 585, 14);
		encryptPanel.add(progressBar);
		
		totalLabel = new JLabel("");
		totalLabel.setBounds(270, 92, 159, 15);
		encryptPanel.add(totalLabel);
		
		//ActionListener for the fromButton
		fromButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

				JFileChooser c = new JFileChooser();
				c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				c.setDialogTitle("Select path to save");
				int result = c.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					String path = c.getSelectedFile().getAbsolutePath();
					fromTextField.setText(path);// show the path in textField
					// read all the files in the directory and prepare data for
					File file = new File(path);
					File[] tempList = file.listFiles();
					System.out.println("该目录下对象个数：" + tempList.length);
					totalLabel.setText("共有 "+tempList.length+" 个文件");
					for (int i = 0; i < tempList.length; i++)
					{
						if (tempList[i].isFile())
						{
							Vector rowData = new Vector();
							System.out.println("文     件：" + tempList[i]
									+ tempList[i].getName());
							rowData.addElement(tempList[i].getName());
							rowData.addElement(true);
							data.addElement(rowData); //add a row of data to the table 
						}
					}
					//refresh data in the table,and set width of column
					model.setDataVector(data, columnNames);
					table.getColumnModel().getColumn(0).setPreferredWidth(200);
				}
			}
		});

		JButton encryptButton = new JButton("开始加密");
		encryptButton.setBounds(312, 383, 103, 36);
		encryptPanel.add(encryptButton);
		
				JPanel userManagePanel = new JPanel();
				tabbedPane.addTab("用户管理", new ImageIcon("./icon/d.png"), userManagePanel, null);
				userManagePanel.setLayout(null);
				
//				JList list = new JList();
//				
//				JScrollPane scroll = new JScrollPane(list);
//				scroll.setBounds(22, 26, 359, 372);
//				userManagePanel.add(scroll);
//				
//				JButton addButton = new JButton("新  增");
//				addButton.setBounds(422, 33, 93, 22);
//				userManagePanel.add(addButton);
//				
//				JButton updateButton = new JButton("修  改");
//				updateButton.setBounds(422, 88, 93, 23);
//				userManagePanel.add(updateButton);
//				
//				JButton deleteButton = new JButton("删  除");
//				deleteButton.setBounds(422, 142, 93, 22);
//				userManagePanel.add(deleteButton);
//				
//				JPanel userUpdatePanel = new JPanel();
//				userUpdatePanel.setBorder(new EtchedBorder());
//				userUpdatePanel.setBounds( 422, 200, 244, 198);
//				userManagePanel.add(userUpdatePanel);
//				userUpdatePanel.setLayout(null);
//				
//				JLabel userNameLabel = new JLabel("用户名");
//				userNameLabel.setBounds(25, 16, 54, 15);
//				userUpdatePanel.add(userNameLabel);
//				
//				usernameTextField = new JTextField();
//				usernameTextField.setBounds(25, 45, 186, 21);
//				userUpdatePanel.add(usernameTextField);
//				usernameTextField.setColumns(10);
//				
//				JLabel passwordLabel = new JLabel("密  码");
//				passwordLabel.setBounds(25, 84, 54, 15);
//				userUpdatePanel.add(passwordLabel);
//				
//				passwordTextField = new JTextField();
//				passwordTextField.setBounds(25, 112, 186, 21);
//				userUpdatePanel.add(passwordTextField);
//				passwordTextField.setColumns(10);
//				
//				JButton confirmButton = new JButton("确  认");
//				confirmButton.setBounds(73, 154, 93, 23);
//				userUpdatePanel.add(confirmButton);
//		
		

		JPanel parameterPanel = new JPanel();
		tabbedPane.addTab("加密参数\r\n", new ImageIcon("./icon/b.png"), parameterPanel, null);
		parameterPanel.setLayout(null);
		
		JList list = new JList();
		
		JScrollPane scroll = new JScrollPane(list);
		scroll.setBounds(22, 200, 359, 161);
		parameterPanel.add(scroll);
		
		JButton addButton = new JButton("新  增");
		addButton.setBounds(22, 376, 93, 22);
		parameterPanel.add(addButton);
		
		JButton updateButton = new JButton("修  改");
		updateButton.setBounds(158, 376, 93, 23);
		parameterPanel.add(updateButton);
		
		JButton deleteButton = new JButton("删  除");
		deleteButton.setBounds(288, 376, 93, 22);
		parameterPanel.add(deleteButton);
		
		JPanel userUpdatePanel = new JPanel();
		userUpdatePanel.setBorder(new EtchedBorder());
		userUpdatePanel.setBounds( 422, 200, 244, 198);
		parameterPanel.add(userUpdatePanel);
		userUpdatePanel.setLayout(null);
		
		JLabel beginIpLabel = new JLabel("起始IP地址");
		beginIpLabel.setBounds(25, 16, 85, 15);
		userUpdatePanel.add(beginIpLabel);
		
		beginIpTextField = new JTextField();
		beginIpTextField.setBounds(25, 45, 186, 21);
		userUpdatePanel.add(beginIpTextField);
		beginIpTextField.setColumns(10);
		
		JLabel endIpLabel = new JLabel("结束IP地址");
		endIpLabel.setBounds(25, 84, 85, 15);
		userUpdatePanel.add(endIpLabel);
		
		endIpTextField = new JTextField();
		endIpTextField.setBounds(25, 112, 186, 21);
		userUpdatePanel.add(endIpTextField);
		endIpTextField.setColumns(10);
		
		JButton confirmButton = new JButton("确  认");
		confirmButton.setBounds(73, 154, 93, 23);
		userUpdatePanel.add(confirmButton);
		
		JPanel RightsPanel = new JPanel();
		RightsPanel.setBounds(21, 6, 645, 182);
		Border rightsTitledBorder = new TitledBorder("权限");
		RightsPanel.setBorder(rightsTitledBorder);
		parameterPanel.add(RightsPanel);
		RightsPanel.setLayout(null);
		
		JCheckBox onlineDisplayCheckBox = new JCheckBox("在线打开");
		onlineDisplayCheckBox.setBounds(64, 43, 118, 25);
		RightsPanel.add(onlineDisplayCheckBox);
		
		JCheckBox onlinePrintCheckBox = new JCheckBox("在线打印");
		onlinePrintCheckBox.setBounds(255, 43, 118, 25);
		RightsPanel.add(onlinePrintCheckBox);
		
		JCheckBox onlineCopyCheckBox = new JCheckBox("在线复制");
		onlineCopyCheckBox.setBounds(453, 43, 118, 25);
		RightsPanel.add(onlineCopyCheckBox);
		
		JCheckBox offlineDisplayCheckBox = new JCheckBox("借阅打开");
		offlineDisplayCheckBox.setBounds(64, 112, 118, 25);
		RightsPanel.add(offlineDisplayCheckBox);
		
		JLabel offlineDisplayDurationLabel = new JLabel("借阅打开时间");
		offlineDisplayDurationLabel.setBounds(255, 116, 82, 17);
		RightsPanel.add(offlineDisplayDurationLabel);
		
		offlineDisplayDurationTextField = new JTextField();
		offlineDisplayDurationTextField.setText("MAX");
		offlineDisplayDurationTextField.setBounds(337, 113, 50, 23);
		RightsPanel.add(offlineDisplayDurationTextField);
		offlineDisplayDurationTextField.setColumns(10);
		
		JLabel offlineDisplayCountLabel = new JLabel("借阅打开次数");
		offlineDisplayCountLabel.setBounds(453, 116, 72, 17);
		RightsPanel.add(offlineDisplayCountLabel);
		
		offlineDisplayCountTextField = new JTextField();
		offlineDisplayCountTextField.setText("MAX");
		offlineDisplayCountTextField.setBounds(537, 113, 44, 23);
		RightsPanel.add(offlineDisplayCountTextField);
		offlineDisplayCountTextField.setColumns(10);

		JPanel fileTransmissionPanel = new JPanel();
		tabbedPane.addTab("文档传输", new ImageIcon("./icon/c.png"), fileTransmissionPanel, null);
		fileTransmissionPanel.setLayout(null);

		JPanel logPanel = new JPanel();
		tabbedPane.addTab("日志管理", new ImageIcon("./icon/e.png"), logPanel, null);
	}
}
