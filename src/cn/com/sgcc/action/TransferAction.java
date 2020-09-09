package cn.com.sgcc.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.com.sgcc.crypto.Cipher;
import cn.com.sgcc.crypto.CipherImpl;
import cn.com.sgcc.crypto.EncryptResult;
import cn.com.sgcc.db.DatabaseLayer;
import cn.com.sgcc.ui.UserManagePanel;
import cn.com.sgcc.vo.*;
import cn.com.sgcc.vo.Rights;
import net.sf.json.JSONObject;

public class TransferAction extends ActionSupport{

	private Cipher cipher;
	private List<FileEncrypted> list = new ArrayList<FileEncrypted>();
	private static final String sourceSuffix="gwbz";
	private static final String fromPath=constant.fromPath;
	private static final String toPath=constant.toPath;
	private DatabaseLayer databaseLayer = new DatabaseLayer();
	private static Log logger = LogFactory.getLog(UserManagePanel.class);
	private int order =1;

	public void setDatabaseLayer(DatabaseLayer databaseLayer)
	{
		this.databaseLayer = databaseLayer;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	
	public String transferListMET() throws Exception {
		System.out.println("********transferListMET********");
		File file = new File(toPath);
		int count=0;
		order =1;
		count=findAllfiles(toPath,file,count);
		JSONObject json = new JSONObject();
		json.accumulate("Rows", list);
		json.accumulate("Count", count);
		System.out.println(list);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/x-json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache"); 
		response.getWriter().print(json.toString());
		return null;
	}
	
	public String downloadMET() throws Exception {
		System.out.println("********encryptionMET********");
		return null;
	}
	
	public int findAllfiles(String path,File file,int count)
	{
		File[] tempList = file.listFiles();
		System.out.println(tempList);
		for (int i = 0; i < tempList.length; i++)
		{
			if (tempList[i].isFile())
			{
				if(tempList[i].getName().endsWith(sourceSuffix.toLowerCase())||tempList[i].getName().endsWith(sourceSuffix.toUpperCase()))
				{
					count++;
					String str=tempList[i].getPath().substring(path.length()+1);
					FileEncrypted fileEncrypted= new FileEncrypted();
					System.out.println("文     件：" + tempList[i] + tempList[i].getName());
					fileEncrypted.setOrder(order++);
					fileEncrypted.setFileName(tempList[i].getName());
					fileEncrypted.setFileDir(str);
					list.add(fileEncrypted); //add a row of data to the table
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
	
}
