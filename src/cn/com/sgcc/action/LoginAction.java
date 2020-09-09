package cn.com.sgcc.action;


import java.util.Map;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.commons.logging.*;
import cn.com.sgcc.db.*;
import cn.com.sgcc.ui.UserManagePanel;
import cn.com.sgcc.vo.*;
import net.sf.json.JSONObject;

public class LoginAction extends ActionSupport {

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

	private static final long serialVersionUID = 1L;
    HttpServletRequest request = ServletActionContext.getRequest();
	
	public String logIn() {
		System.out.println("********logIn********");
		System.out.println(this.name + "  "+this.password);
		try {
			if(null == name) {
				return null;
			}
			System.out.println(databaseLayer.getUserDao().select(name));
			if(databaseLayer.getUserDao().select(name).getPassword().equals(password)){
				System.out.println("if");
				Map<String, Object> session = ActionContext.getContext().getSession();
				session.put("login_user", name);
				JSONObject json = new JSONObject();
				json.accumulate("msg", "SUCCESS");
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/x-json;charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache"); 
				response.getWriter().print(json.toString());
				return null;
			}else{
				System.out.println("else");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String logOut() {
		System.out.println("********logOut********");
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.remove("login_user");
		name = null;
		return SUCCESS;
	}

}
