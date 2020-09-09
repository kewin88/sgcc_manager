package cn.com.sgcc.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class NormalFileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String fromPath=constant.fromPath;
	private static final String toPath=constant.toPath;

//	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8"); // 设置处理请求的编码方式
		response.setContentType("text/html;charset=UTF-8"); // 设置Content-Type字段值
		PrintWriter out = response.getWriter();

		// 实例化一个硬盘文件工厂，用来配置文件上传组件ServletFileUpload

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory); // 利用硬盘文件工厂配置文件上传组件
		List<FileItem> items = null; // 存放FileItem对象
		try { // 获取文件的FileItem对象，即表单域,分为普通表单域和文件域
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		String path = null; // 存放上传文件的完整名称，包括路径。
		String filename = " "; // 存放文件名
		InputStream is = null;
		// 循环处理上传文件
		for (FileItem item : items) {
			if (item.isFormField()) {
				if (item.getFieldName().equals("filename")) {
					if (!item.getString().equals(""))
						filename = item.getString("UTF-8");
				}
			} else if (item.getName().trim() != null
					&& !item.getName().trim().equals(" ")) {
				path = item.getName();// 得到文件完整路径
				filename = path.substring(path.lastIndexOf("\\") + 1);
				is = item.getInputStream(); // 获得上传文件的InputStream对象
			}
		}
		if(filename == null || filename == ""){
			out.print("<script language=\"javascript\">alert('请选择上传文件！');history.go(-1);</script>");
			return;
		}
		filename = fromPath + '/' + filename;
		if (new File(filename).exists()) {
			out.print("<script language=\"javascript\">alert('已存在同名文件，上传失败！');history.go(-1);</script>");
		} else if (!filename.equals("")) {
			FileOutputStream fos = new FileOutputStream(filename);
			byte[] buffer = new byte[8192];
			int count = 0;

			// 开始读取上传文件的字节，并将其输出到服务器端的上传文件输 出流中
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count); // 向服务器端文件写入字节流
			}
			fos.close();
			is.close();
			out.print("<script language=\"javascript\">alert('上传成功！');history.go(-1);</script>");
		}

	}

}
