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

public class IpRangeAction extends ActionSupport{

	private int id;
	private String beginIp;
	private String endIp;
	public void setId(int id)
	{
		this.id = id;
	}

	public void setBeginIp(String beginIp)
	{
		this.beginIp = beginIp;
	}
	public void setEndIp(String endIp)
	{
		this.endIp = endIp;
	}
	private DatabaseLayer databaseLayer = new DatabaseLayer();
	private static Log logger = LogFactory.getLog(UserManagePanel.class);

	public void setDatabaseLayer(DatabaseLayer databaseLayer)
	{
		this.databaseLayer = databaseLayer;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	
	public String ipRangeListMET() throws Exception {
		System.out.println("********userListMET********");
		List<IpRange> list = databaseLayer.getIpRangeDao().selectAll();
		JSONObject json = new JSONObject();
		json.accumulate("Rows", list);
		System.out.println(list);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/x-json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache"); 
		response.getWriter().print(json.toString());
		return null;
	}
	
	public String ipRangeAddMET() throws Exception {
		System.out.println("********userAddMET********");
		IpRange ipRange = new IpRange();
		ipRange.setBeginIp(this.beginIp);
		ipRange.setEndIp(this.endIp);
		databaseLayer.getIpRangeDao().insert(ipRange);
		return null;
	}
	
	public String ipRangeEditMET() throws Exception {
		System.out.println("********userEditMET********");
		IpRange ipRange = new IpRange();
		ipRange.setId(this.id);
		ipRange.setBeginIp(this.beginIp);
		ipRange.setEndIp(this.endIp);
		databaseLayer.getIpRangeDao().update(ipRange);
		return null;
	}
	
	public String ipRangeDeleteMET() throws Exception {
		System.out.println("********userDeleteMET********");
		databaseLayer.getIpRangeDao().delete(this.id);
		return null;
	}

	
}
