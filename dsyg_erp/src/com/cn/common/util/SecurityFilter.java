package com.cn.common.util;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SecurityFilter extends HttpServlet implements Filter {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public final String www_url_encode= "application/x-www-form-urlencoded";
	public final String mul_data= "multipart/form-data";
	public final String txt_pla= "text/plain";
	public final String html_pla= "text/html";
 
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
 
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		String contenType=request.getHeader("conTent-type");
		
		if(contenType!=null&&!contenType.equals("")&&!contenType.startsWith(www_url_encode)&&!contenType.startsWith(mul_data)&&!contenType.startsWith(txt_pla)&&!contenType.startsWith(html_pla)){			
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("非法请求Content-Type！");
			return;				
		}
		arg2.doFilter(request, response);
	}
 
	public void init(FilterConfig arg0) throws ServletException {
 
	}
}
