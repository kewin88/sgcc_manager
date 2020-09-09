package cn.com.sgcc.ui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import java.util.Vector;

import org.apache.commons.logging.*;

import cn.com.sgcc.vo.*;
import cn.com.sgcc.crypto.*;
import cn.com.sgcc.inform.*;

public class BatchEncryptPanel extends JPanel implements Informer
{
	private static Log logger = LogFactory.getLog(BatchEncryptPanel.class);

	private Cipher cipher;

	private JTextField fromTextField;
	private JTextField toTextField;
	private JTable table;
	private DefaultTableModel model;
	private Vector data = new Vector(); //data for the table
	private Vector columnNames=new Vector(Arrays.asList(new String[]{ "文件", "待加密", "成功", "失败"}));
	private JLabel totalLabel;
	private JButton encryptButton;
	private JProgressBar progressBar;
	private Informer informer = this;
	private static final String sourceSuffix="pdf";
    
	public void setCipher(Cipher cipher)
	{
		this.cipher = cipher;
	}

	public BatchEncryptPanel()
	{
		super();
	}

	
	public void init()
	{
		setLayout(null);

		JLabel fromLabel = new JLabel("源路径");
		fromLabel.setBounds(61, 23, 77, 20);
		add(fromLabel);

		fromTextField = new JTextField();
		fromTextField.setBounds(135, 23, 424, 21);
		add(fromTextField);
		fromTextField.setColumns(10);

		JLabel toLabel = new JLabel("目的路径\r\n");
		toLabel.setBounds(61, 66, 54, 15);
		add(toLabel);

		toTextField = new JTextField();
		toTextField.setBounds(135, 63, 424, 21);
		add(toTextField);
		toTextField.setColumns(10);

		JButton fromButton = new JButton("选择");
		

		fromButton.setBounds(582, 22, 64, 23);
		add(fromButton);

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
		add(toButton);

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
		add(scrollPane);

		progressBar = new JProgressBar();
		progressBar.setBounds(61, 355, 585, 14);
		add(progressBar);
		
		totalLabel = new JLabel("");
		totalLabel.setBounds(270, 92, 159, 15);
		add(totalLabel);
		
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
					// read all the files in the directory and prepare data for table
					File file = new File(path);
					int count=0;
					count=findAllfiles(path,file,count);
					totalLabel.setText("共有 "+count+" 个文件");
					
					
					//refresh data in the table,and set width of column
					model.setDataVector(data, columnNames);
					table.getColumnModel().getColumn(0).setPreferredWidth(200);
				}
			}
		});
		
		
		encryptButton = new JButton("开始加密");
		encryptButton.setBounds(312, 383, 103, 36);
		add(encryptButton);
		encryptButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new Thread(new Runnable()
				{
					public void run()
					{
						String fromDirName = fromTextField.getText().trim();
						String toDirName = toTextField.getText().trim();
						File fromDir = new File(fromDirName);
						File toDir = new File(toDirName);

						Rights rights;
						try
						{
						    rights = MainFrame.rights;
//						    System.out.println(rights.getOnlineDisplayable());
							//rights = rightsPanel.getRights();
						}
						catch (NumberFormatException e)
						{
							JOptionPane.showMessageDialog(null, "借阅小时数和次数必须是正整数！", "错误", JOptionPane.ERROR_MESSAGE);
							return;
						}

						EncryptResult result=cipher.encryptDir(fromDir, toDir, rights, false);
						
						for( int i=0; i<data.size(); i++ )
						{
							Object object=((Vector)data.get(i)).get(0);
							if(result.FailedFiles.contains(object))
							{
								((Vector)data.get(i)).setElementAt(false, 1);
								((Vector)data.get(i)).setElementAt(false, 2);
								((Vector)data.get(i)).setElementAt(true, 3);
							}
							else
							{
								((Vector)data.get(i)).setElementAt(false, 1);
								((Vector)data.get(i)).setElementAt(true, 2);
								((Vector)data.get(i)).setElementAt(false, 3);
							}
							
						}
						model.setDataVector(data, columnNames);
						table.getColumnModel().getColumn(0).setPreferredWidth(200);
					}
				}).start();
			}
		});
		
	}

	public EncryptResult Encryption(String fromDirName,String toDirName){
		File fromDir = new File(fromDirName);
		File toDir = new File(toDirName);

		Rights rights = new Rights();

		EncryptResult result=cipher.encryptDir(fromDir, toDir, rights, false);
		return result;
	}
	//the function used to find all files in the directory
	public int findAllfiles(String path,File file,int count)
	{
		
		File[] tempList = file.listFiles();
//		System.out.println("该目录下对象个数：" + tempList.length);
//		System.out.println("total:"+count);

		for (int i = 0; i < tempList.length; i++)
		{
			if (tempList[i].isFile())
			{
				if(tempList[i].getName().endsWith(sourceSuffix.toLowerCase())||tempList[i].getName().endsWith(sourceSuffix.toUpperCase()))
				{
					count++;
					String str=tempList[i].getPath().substring(path.length()+1);
					Vector rowData = new Vector();
					System.out.println("文     件：" + tempList[i]
						+ tempList[i].getName());
					rowData.addElement(str);
					rowData.addElement(true);
					data.addElement(rowData); //add a row of data to the table
				}
			}
		}	
		for(int i=0;i<tempList.length;i++)
		{
			if(tempList[i].isDirectory())
			{
				count=findAllfiles(path,tempList[i], count);
			}
		}
		return count;
	}
	private void setDefaultRights()
	{
		Rights rights = new Rights();
		rights.setOnlineDisplayable(true);
		//rightsPanel.setRights(rights);
	}

	@Override
	public void informError(String msg)
	{
		JOptionPane.showMessageDialog(null, msg, "错误", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void informReady(String msg)
	{
		encryptButton.setEnabled(false);
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressBar.setString(msg);
	}

	@Override
	public void informStart(EncryptResult result)
	{
		progressBar.setIndeterminate(false);
		progressBar.setMaximum(result.NumberOfAll);
		progressBar.setValue(0);
		progressBar.setString("开始加密");
	}

	@Override
	public void informProgress(EncryptResult result)
	{
		progressBar.setValue(result.NumberOfFail + result.NumberOfNewEncrypt + result.NumberOfOldEncrypt + result.NumberOfReEncrypt);
		StringBuilder sb = new StringBuilder();
		sb.append("共");
		sb.append(result.NumberOfAll);
		sb.append("个，成功");
		sb.append(result.NumberOfNewEncrypt + result.NumberOfOldEncrypt + result.NumberOfReEncrypt);
		sb.append("个，出错");
		sb.append(result.NumberOfFail);
		sb.append("个");
		progressBar.setString(sb.toString());
	}

	@Override
	public void informEnd(EncryptResult result)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("共");
		sb.append(result.NumberOfAll);
		sb.append("个，成功");
		sb.append(result.NumberOfNewEncrypt + result.NumberOfOldEncrypt + result.NumberOfReEncrypt);
		sb.append("个，出错");
		sb.append(result.NumberOfFail);
		sb.append("个");
		JOptionPane.showMessageDialog(null, sb.toString(), "信息", JOptionPane.INFORMATION_MESSAGE);
		encryptButton.setEnabled(true);
	}
}
