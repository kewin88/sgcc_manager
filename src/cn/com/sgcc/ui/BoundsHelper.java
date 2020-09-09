package cn.com.sgcc.ui;

import java.awt.*;

public class BoundsHelper
{
	public static final int HEIGHT = 20;

	public static void setBounds(Component c, int x, int y, int width, int height)
	{
		c.setBounds(x, y, width, height);
	}

	public static void setBounds(Component c, int x, int y, int width)
	{
		c.setBounds(x, y, width, HEIGHT);
	}

	public static void setBounds(Component c, int x, int y)
	{
		c.setBounds(x, y, (int) c.getPreferredSize().getWidth(), HEIGHT);
	}
}
