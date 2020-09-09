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

public class UserAction extends ActionSupport{
	private int id;
	private String name ;
	private String password;
	public void setId(int id)
	{
		this.id = id;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	private DatabaseLayer databaseLayer = new DatabaseLayer();
	private static Log logger = LogFactory.getLog(UserManagePanel.class);

	public void setDatabaseLayer(DatabaseLayer databaseLayer)
	{
		this.databaseLayer = databaseLayer;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	
	public String userListMET() throws Exception {
		System.out.println("********userListMET********");
		List<User> list = databaseLayer.getUserDao().selectAll();
		JSONObject json = new JSONObject();
		json.accumulate("Rows", list);
		System.out.println(list);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/x-json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache"); 
		response.getWriter().print(json.toString());
		return null;
	}
	
	public String userAddMET() throws Exception {
		System.out.println("********userAddMET********");
		User user = new User();
		user.setName(this.name);
		user.setPassword(this.password);
		databaseLayer.getUserDao().insert(user);
		return null;
	}
	
	public String userEditMET() throws Exception {
		System.out.println("********userEditMET********");
		User user = new User();
		user.setId(this.id);
		user.setName(this.name);
		user.setPassword(this.password);
		databaseLayer.getUserDao().update(user);
		return null;
	}
	
	public String userDeleteMET() throws Exception {
		System.out.println("********userDeleteMET********");
		databaseLayer.getUserDao().delete(this.id);
		return null;
	}
	public String userLoginMET() throws Exception {
		System.out.println("********userLoginMET********");
		System.out.println(this.name);
		System.out.println(databaseLayer.getUserDao().select(this.name));
		return null;
	}

	


}