package cn.com.sgcc.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String fromPath=constant.fromPath;
	private static final String toPath=constant.toPath;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String filename = new String(request.getParameter("fileName").getBytes("iso-8859-1"), "utf-8");  
		 String fileDir = new String(request.getParameter("fileDir").getBytes("iso-8859-1"), "utf-8");
	        System.out.println("hello"+filename);  
	          
	        //设置文件MIME类型  
	        response.setContentType(getServletContext().getMimeType(filename));  
	        //设置Content-Disposition  
	        response.setHeader("Content-Disposition", "attachment;filename="+ new String(filename.getBytes("utf-8"), "ISO-8859-1"));  
	        //读取目标文件，通过response将目标文件写到客户端  
	        //获取目标文件的绝对路径  
	        String fullFileName = toPath + '/' + fileDir;  
	        //System.out.println(fullFileName);  
	        //读取文件  
	        InputStream in = new FileInputStream(fullFileName);  
	        OutputStream out = response.getOutputStream();  
	          
	        //写文件  
	        int b;  
	        while((b=in.read())!= -1)  
	        {  
	            out.write(b);  
	        }  
	        in.close();  
	        out.close();  
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
