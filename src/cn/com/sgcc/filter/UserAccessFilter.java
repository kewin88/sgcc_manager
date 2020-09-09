package cn.com.sgcc.filter;  
  
import java.io.IOException;  
  
import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
  
public class UserAccessFilter implements Filter{  
  
    @Override  
    public void destroy() {  
        System.out.println("destroy!");  
    }  
  
    @Override  
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)   
            throws IOException, ServletException {    
        HttpServletRequest request = (HttpServletRequest)req;   
        HttpServletResponse response = (HttpServletResponse)res;  
        HttpSession session = request.getSession();  
        System.out.println(request.getRequestURI());
        System.out.println(session.getAttribute("login_user"));
        if(session.getAttribute("login_user")== null && request.getRequestURI().indexOf("login.jsp")==-1 ){   
            response.sendRedirect("login.jsp");   
            return ;   
        }else{
        	chain.doFilter(req, res);  
        } 
    }  
  
    @Override  
    public void init(FilterConfig config) throws ServletException {  
        //ApplicationFilterConfig[name=UserFilter, filterClass=com.lzw.filter.demo.UserAccessFilter]  
        System.out.println(config.toString());  
    }  
  
}  