package cn.com.sgcc.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.logging.*;
import cn.com.sgcc.db.*;
import cn.com.sgcc.ui.MainFrame;
import cn.com.sgcc.ui.UserManagePanel;
import cn.com.sgcc.crypto.Cipher;
import cn.com.sgcc.crypto.CipherImpl;
import cn.com.sgcc.crypto.EncryptResult;
import cn.com.sgcc.dao.*;
import cn.com.sgcc.vo.*;
import cn.com.sgcc.inform.*;
import cn.com.sgcc.ui.*;

public class EncryptionAction extends ActionSupport {

	private Cipher cipher;
	private List<FileEncryption> list = new ArrayList<FileEncryption>();
	private static final String sourceSuffix = "pdf";
	private static final String fromPath=constant.fromPath;
	private static final String toPath=constant.toPath;
	private DatabaseLayer databaseLayer = new DatabaseLayer();
	private static Log logger = LogFactory.getLog(UserManagePanel.class);
	private int order = 1;

	public void setDatabaseLayer(DatabaseLayer databaseLayer) {
		this.databaseLayer = databaseLayer;
	}

	HttpServletRequest request = ServletActionContext.getRequest();

	public String encryptionListMET() throws Exception {
		System.out.println("********encryptionListMET********");
		File file = new File(fromPath);
		int count = 0;
		order = 1;
		count = findAllfiles(fromPath, file, count);
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

	public String encryptionMET() throws Exception {
		System.out.println("********encryptionMET********");
		cipher = new CipherImpl();
		// new Thread(new Runnable()
		// {
		// public void run()
		// {
		List<Rights> rightsList = databaseLayer.getRightsDao().selectAll();
		Rights rights = rightsList.get(0);
		File fromDir = new File(fromPath.trim());
		File toDir = new File(toPath.trim());
		EncryptResult result = cipher.encryptDir(fromDir, toDir, rights, false);
		System.out.println(result);
		int count = 0;
		order = 1;
		count = findAllfiles(fromPath, fromDir, count);
		for (int i = 0; i < list.size(); i++) {
			FileEncryption file = list.get(i);
			if (result.FailedFiles.contains(file.getFileName())) {
				list.get(i).setIsNotEncrypted("");
				list.get(i).setIsSuccessed("");
				list.get(i).setIsFailed("是");
			} else {
				list.get(i).setIsNotEncrypted("");
				list.get(i).setIsSuccessed("是");
				list.get(i).setIsFailed("");
			}
		}
		String str = informEnd(result);
		System.out.println(str);
		JSONObject json = new JSONObject();
		json.accumulate("Rows", list);
		json.accumulate("Info", str);
		System.out.println(list);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/x-json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().print(json.toString());
		// }
		// }).start();
		//
		return null;
	}

	public int findAllfiles(String path, File file, int count) {
		File[] tempList = file.listFiles();
		System.out.println(tempList);
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				if (tempList[i].getName().endsWith(sourceSuffix.toLowerCase())
						|| tempList[i].getName().endsWith(sourceSuffix.toUpperCase())) {
					count++;
					String str = tempList[i].getPath().substring(path.length() + 1);
					FileEncryption fileEncryption = new FileEncryption();
					System.out.println("文     件：" + tempList[i] + tempList[i].getName());
					fileEncryption.setOrder(order++);
					fileEncryption.setFileName(tempList[i].getName());
					fileEncryption.setFileDir(str);
					fileEncryption.setIsNotEncrypted("是");
					list.add(fileEncryption); // add a row of data to the table
				}
			}
		}
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isDirectory()) {
				count = findAllfiles(path, tempList[i], count);
			}
		}
		return count;
	}

	public String informEnd(EncryptResult result) {
		StringBuilder sb = new StringBuilder();
		sb.append("共");
		sb.append(result.NumberOfAll);
		sb.append("个，成功");
		sb.append(result.NumberOfNewEncrypt + result.NumberOfOldEncrypt + result.NumberOfReEncrypt);
		sb.append("个，出错");
		sb.append(result.NumberOfFail);
		sb.append("个，新加密");
		sb.append(result.NumberOfNewEncrypt);
		sb.append("个");
		String str = sb.toString();
		return str;
	}
}
