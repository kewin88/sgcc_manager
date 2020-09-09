package cn.com.sgcc.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import cn.com.sgcc.vo.*;

public class IpRangeListCellRenderer extends JLabel implements ListCellRenderer
{
	public Component getListCellRendererComponent(
			JList list,              // the list
			Object value,            // value to display
			int index,               // cell index
			boolean isSelected,      // is the cell selected
			boolean cellHasFocus)    // does the cell have focus
	{
		IpRange ipRange = (IpRange) value;

		setText(ipRange.getBeginIp() + " --> " + ipRange.getEndIp());

		if (isSelected) 
		{
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} 
		else 
		{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);

		return this;
	}
}
