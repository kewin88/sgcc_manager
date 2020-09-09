package cn.com.sgcc.action;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.logging.*;
import cn.com.sgcc.db.*;
import cn.com.sgcc.ui.UserManagePanel;
import cn.com.sgcc.dao.*;
import cn.com.sgcc.vo.*;

public class RightsAction extends ActionSupport{
	private int id;
	private boolean onlineDisplayable;
	private boolean onlinePrintable;
	private boolean onlineCopyable;
	private boolean offlineDisplayable;
	private int offlineDisplayDuration;
	private int offlineDisplayCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public boolean getOnlineDisplayable()
	{
		return onlineDisplayable;
	}

	public void setOnlineDisplayable(boolean onlineDisplayable)
	{
		this.onlineDisplayable = onlineDisplayable;
	}

	public boolean getOnlinePrintable()
	{
		return onlinePrintable;
	}

	public void setOnlinePrintable(boolean onlinePrintable)
	{
		this.onlinePrintable = onlinePrintable;
	}

	public boolean getOnlineCopyable()
	{
		return onlineCopyable;
	}

	public void setOnlineCopyable(boolean onlineCopyable)
	{
		this.onlineCopyable = onlineCopyable;
	}

	public boolean getOfflineDisplayable()
	{
		return offlineDisplayable;
	}

	public void setOfflineDisplayable(boolean offlineDisplayable)
	{
		this.offlineDisplayable = offlineDisplayable;
	}

	public int getOfflineDisplayDuration()
	{
		return offlineDisplayDuration;
	}

	public void setOfflineDisplayDuration(int offlineDisplayDuration)
	{
		this.offlineDisplayDuration = offlineDisplayDuration;
	}

	public int getOfflineDisplayCount()
	{
		return offlineDisplayCount;
	}

	public void setOfflineDisplayCount(int offlineDisplayCount)
	{
		this.offlineDisplayCount = offlineDisplayCount;
	}
	private DatabaseLayer databaseLayer = new DatabaseLayer();
	private static Log logger = LogFactory.getLog(UserManagePanel.class);

	public void setDatabaseLayer(DatabaseLayer databaseLayer)
	{
		this.databaseLayer = databaseLayer;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	
	public String rightsListMET() throws Exception {
		System.out.println("********rightsListMET********");
		List<Rights> list = databaseLayer.getRightsDao().selectAll();
		Rights rights = list.get(0);
		JSONObject json = new JSONObject();
		json.accumulate("rights", rights);
		System.out.println(list);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/x-json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache"); 
		response.getWriter().print(json.toString());
		return null;
	}
	
	public String rightsAddMET() throws Exception {
		System.out.println("********rightsAddMET********");
		databaseLayer.getRightsDao().delete();
		Rights rights = new Rights();
		rights.setOnlineDisplayable(this.onlineDisplayable);
		rights.setOnlinePrintable(this.onlinePrintable);
		rights.setOnlineCopyable(this.onlineCopyable);
		rights.setOfflineDisplayable(this.offlineDisplayable);
		rights.setOfflineDisplayDuration(this.offlineDisplayDuration);
		rights.setOfflineDisplayCount(this.offlineDisplayCount);
		System.out.println(rights);
		databaseLayer.getRightsDao().insert(rights);
		
		return null;
	}
}