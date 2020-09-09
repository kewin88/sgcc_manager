package cn.com.sgcc.ui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;

import org.apache.commons.logging.*;
import org.jvnet.substance.*;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.jvnet.substance.theme.SubstanceEbonyTheme;
import org.jvnet.substance.title.FlatTitlePainter;
import org.jvnet.substance.watermark.SubstanceBinaryWatermark;

import cn.com.sgcc.db.*;
import cn.com.sgcc.vo.Rights;

public class MainFrame extends JFrame implements ChangeListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(MainFrame.class);

	private JTabbedPane tabbedPane;
	private BatchEncryptPanelRRR encryptPanel;
	public IpRangeConfigPanel ipConfigPanel;
	private RightsConfigPanel rightsConfigPanel;
	private UserManagePanel userManagePanel;
	private LogPanel logPanel;
	private FileTransmissionPanel fileTransmissionPanel=new FileTransmissionPanel();
	public static Rights rights=null;
	
	public static void changeRights(Rights rights)
	{
		MainFrame.rights=rights;
	}
	
	public void setEncryptPanel(BatchEncryptPanelRRR encryptPanel)
	{
		this.encryptPanel = encryptPanel;
	}

	public void setIpConfigPanel(IpRangeConfigPanel ipConfigPanel)
	{
		this.ipConfigPanel = ipConfigPanel;
	}

	public void setRightsConfigPanel(RightsConfigPanel rightsConfigPanel)
	{
		this.rightsConfigPanel = rightsConfigPanel;
	}

	public void setUserManagePanel(UserManagePanel userManagePanel) {
		this.userManagePanel = userManagePanel;
	}

	public void setLogPanel(LogPanel logPanel) {
		this.logPanel = logPanel;
	}
	
	public void setFileTransmissionPanel(FileTransmissionPanel fileTransmissionPanel)
	{
		this.fileTransmissionPanel=fileTransmissionPanel;
	}

	public MainFrame()
	{
		super();
		setBounds(100, 100, 747, 497);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setIconImage(new ImageIcon("./icon/main.png").getImage());
		setTitle("国家电网安全电子文档加密客户端");
		
		beCenter();
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 731, 458);
		getContentPane().add(tabbedPane);
		
		tabbedPane.addChangeListener(new ChangeListener(){
		    @Override public void stateChanged(ChangeEvent e){
		        System.out.println(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex())+tabbedPane.getSelectedIndex());
		        if(tabbedPane.getSelectedIndex()==0)
		        {
		        	changeRights(ipConfigPanel.getRights());
		        }
		    }
		});
	}

	public void init()
	{
		encryptPanel.init();
		ipConfigPanel.initialize();
		rightsConfigPanel.init();
		userManagePanel.init();
		logPanel.init();
		fileTransmissionPanel.init();
		tabbedPane.addTab("加密管理", new ImageIcon("./icon/a.png"), encryptPanel, null);
		encryptPanel.setLayout(null);
//		JPanel parameterPanel = new JPanel();
		tabbedPane.addTab("加密参数\r\n", new ImageIcon("./icon/b.png"), ipConfigPanel, null);

		
		tabbedPane.addTab("文档传输", new ImageIcon("./icon/c.png"), fileTransmissionPanel, null);
		fileTransmissionPanel.setLayout(null);

//		JPanel userManagePanel = new JPanel();
		tabbedPane.addTab("用户管理", new ImageIcon("./icon/d.png"), userManagePanel, null);

//		JPanel logPanel = new JPanel();
		tabbedPane.addTab("日志管理", new ImageIcon("./icon/e.png"), logPanel, null);
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

	@Override
	public void stateChanged(ChangeEvent arg0)
	{
		
		
	}
	
}
