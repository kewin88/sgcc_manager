package cn.com.sgcc.ui;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

import org.apache.commons.logging.*;
import org.jvnet.substance.SubstanceButtonUI;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.BusinessBlueSteelSkin;
import org.jvnet.substance.skin.EmeraldDuskSkin;
import org.jvnet.substance.skin.SubstanceAutumnLookAndFeel;
import org.jvnet.substance.skin.SubstanceBusinessLookAndFeel;
import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;
import org.jvnet.substance.theme.SubstanceBarbyPinkTheme;
import org.jvnet.substance.theme.SubstanceCharcoalTheme;
import org.jvnet.substance.theme.SubstanceEbonyTheme;
import org.jvnet.substance.theme.SubstanceOrangeTheme;
import org.jvnet.substance.theme.SubstanceTerracottaTheme;
import org.jvnet.substance.theme.SubstanceTheme;
import org.jvnet.substance.title.FlatTitlePainter;
import org.jvnet.substance.title.MatteHeaderPainter;
import org.jvnet.substance.watermark.SubstanceBinaryWatermark;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;
import org.jvnet.substance.watermark.SubstanceStripeWatermark;

import cn.com.sgcc.db.*;

public class LoginFrame extends JFrame
{
	private static Log logger = LogFactory.getLog(LoginFrame.class);

	private LoginPanel loginPanel;

	public void setLoginPanel(LoginPanel loginPanel)
	{
		this.loginPanel = loginPanel;
	}

	public LoginFrame()
	{
		super();
		setResizable(false);
		setIconImage(new ImageIcon("./icon/main.png").getImage());
		setTitle("国家电网安全电子文档加密客户端");
		setBounds(100, 100, 528, 342);
		//setLayout(null);

		
		//System.out.println("loginframe start");
		beCenter();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		 try {  
			    UIManager.setLookAndFeel(new SubstanceLookAndFeel());
			    SubstanceLookAndFeel.setCurrentTheme(new SubstanceTerracottaTheme());
			    JFrame.setDefaultLookAndFeelDecorated(true);
			    JDialog.setDefaultLookAndFeelDecorated(true); 
			    SubstanceLookAndFeel.setCurrentTheme(new SubstanceTerracottaTheme());
			    //设置皮肤
	            SubstanceLookAndFeel.setSkin(new BusinessBlueSteelSkin());     
	            //设置按钮形状
	            SubstanceLookAndFeel.setCurrentButtonShaper(new StandardButtonShaper());
	            //设置水印  
                SubstanceLookAndFeel.setCurrentWatermark(new SubstanceStripeWatermark());  
                //设置边框  
                SubstanceLookAndFeel.setCurrentBorderPainter(new StandardBorderPainter());  
                //设置渐变渲染  
                SubstanceLookAndFeel.setCurrentGradientPainter(new StandardGradientPainter());  
                //设置标题  
                SubstanceLookAndFeel.setCurrentTitlePainter(new MatteHeaderPainter()); 
	            
//				String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
//				UIManager.setLookAndFeel(lookAndFeel);
				SwingUtilities.updateComponentTreeUI(this);
	        } catch (Exception e) {  
	            System.err.println("Something went wrong!");  
	        }  
		
//		try
//		{
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//			SwingUtilities.updateComponentTreeUI(this);
//		}
//		catch (Exception e)
//		{
//			logger.warn("WindowsLookAndFeel unsupported!");
//		}
	}

	public void init()
	{
		//loginPanel.init();
		add(loginPanel);
		setVisible(true);
	}

	private void beCenter()
	{
		Toolkit kit = Toolkit.getDefaultToolkit();
   		Dimension screenSize = kit.getScreenSize();
   		int screenHeight = screenSize.height;
   		int screenWidth = screenSize.width;

   		int frameH = getHeight();
   		int frameW = getWidth();
   		setLocation((screenWidth - frameW) / 2, (screenHeight - frameH) / 2);
	}
	
}
