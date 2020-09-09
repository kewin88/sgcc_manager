package cn.com.sgcc.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import cn.com.sgcc.vo.*;

public class RightsPanel extends JPanel
{
	private Border rightsTitledBorder;
	private JCheckBox onlineDisplayCheckBox;
	private JCheckBox onlinePrintCheckBox;
	private JCheckBox onlineCopyCheckBox;
	private JCheckBox offlineDisplayCheckBox;
	private JLabel offlineDisplayDurationLabel;
	private JTextField offlineDisplayDurationTextField;
	private JLabel offlineDisplayCountLabel;
	private JTextField offlineDisplayCountTextField;

	public RightsPanel()
	{
		super();
		initialize();
	}
	
	private void initialize()
	{
		rightsTitledBorder = new TitledBorder("权限");
		setBorder(rightsTitledBorder);
		setBounds(21, 6, 645, 182);
		setLayout(null);

		onlineDisplayCheckBox = new JCheckBox("在线打开");
		onlineDisplayCheckBox.setBounds(64, 43, 118, 25);
		add(onlineDisplayCheckBox);
		
		onlinePrintCheckBox = new JCheckBox("在线打印");
		onlinePrintCheckBox.setBounds(255, 43, 118, 25);
		add(onlinePrintCheckBox);
		
		onlineCopyCheckBox = new JCheckBox("在线复制");
		onlineCopyCheckBox.setBounds(453, 43, 118, 25);
		add(onlineCopyCheckBox);
		
		offlineDisplayCheckBox = new JCheckBox("借阅打开");
		offlineDisplayCheckBox.setBounds(64, 112, 118, 25);
		add(offlineDisplayCheckBox);
		
		offlineDisplayDurationLabel = new JLabel("借阅打开时间");
		offlineDisplayDurationLabel.setBounds(255, 116, 82, 17);
		add(offlineDisplayDurationLabel);
		
		offlineDisplayDurationTextField = new JTextField();
		offlineDisplayDurationTextField.setText("MAX");
		offlineDisplayDurationTextField.setBounds(337, 113, 50, 23);
		add(offlineDisplayDurationTextField);
		offlineDisplayDurationTextField.setColumns(10);
		
		offlineDisplayCountLabel = new JLabel("借阅打开次数");
		offlineDisplayCountLabel.setBounds(453, 116, 72, 19);
		add(offlineDisplayCountLabel);
		
		offlineDisplayCountTextField = new JTextField();
		offlineDisplayCountTextField.setText("MAX");
		offlineDisplayCountTextField.setBounds(537, 113, 44, 23);
		add(offlineDisplayCountTextField);
		offlineDisplayCountTextField.setColumns(10);


		setOfflineEnabled(false);

		offlineDisplayCheckBox.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent ie)
			{
				if (offlineDisplayCheckBox.isSelected())
				{
					setOfflineEnabled(true);
					offlineDisplayDurationTextField.setText("MAX");
					offlineDisplayCountTextField.setText("MAX");
				}
				else
				{
					setOfflineEnabled(false);
					offlineDisplayDurationTextField.setText("MAX");
					offlineDisplayCountTextField.setText("MAX");
				}
			}
		});
	}

	public void setOfflineEnabled(boolean b)
	{
		offlineDisplayDurationLabel.setEnabled(b);
		offlineDisplayDurationTextField.setEnabled(b);
		offlineDisplayCountLabel.setEnabled(b);
		offlineDisplayCountTextField.setEnabled(b);
	}

	public void setEnabled(boolean b)
	{
		onlineDisplayCheckBox.setEnabled(b);
		onlinePrintCheckBox.setEnabled(b);
		onlineCopyCheckBox.setEnabled(b);
		offlineDisplayCheckBox.setEnabled(b);
	}

	public Rights getRights() throws NumberFormatException
	{
		Rights rights = new Rights();
		rights.setOnlineDisplayable(onlineDisplayCheckBox.isSelected());
		rights.setOnlinePrintable(onlinePrintCheckBox.isSelected());
		rights.setOnlineCopyable(onlineCopyCheckBox.isSelected());
		rights.setOfflineDisplayable(offlineDisplayCheckBox.isSelected());

		if (offlineDisplayDurationTextField.getText().trim().equals("MAX"))
		{
			rights.setOfflineDisplayDuration(-1);
		}
		else
		{
			rights.setOfflineDisplayDuration(Integer.parseInt(offlineDisplayDurationTextField.getText().trim()));
		}

		if (offlineDisplayCountTextField.getText().trim().equals("MAX"))
		{
			rights.setOfflineDisplayCount(-1);
		}
		else
		{
			rights.setOfflineDisplayCount(Integer.parseInt(offlineDisplayCountTextField.getText().trim()));
		}

		return rights;
	}

	public void setRights(Rights rights)
	{
		onlineDisplayCheckBox.setSelected(rights.getOnlineDisplayable());
		onlinePrintCheckBox.setSelected(rights.getOnlinePrintable());
		onlineCopyCheckBox.setSelected(rights.getOnlineCopyable());
		offlineDisplayCheckBox.setSelected(rights.getOfflineDisplayable());

		if (rights.getOfflineDisplayDuration() == -1 || rights.getOfflineDisplayDuration() == 0)
		{
			offlineDisplayDurationTextField.setText("MAX");
		}
		else
		{
			offlineDisplayDurationTextField.setText(String.valueOf(rights.getOfflineDisplayDuration()));
		}

		if (rights.getOfflineDisplayCount() == -1 || rights.getOfflineDisplayCount() == 0)
		{
			offlineDisplayCountTextField.setText("MAX");
		}
		else
		{
			offlineDisplayCountTextField.setText(String.valueOf(rights.getOfflineDisplayCount()));
		}
	}
}
