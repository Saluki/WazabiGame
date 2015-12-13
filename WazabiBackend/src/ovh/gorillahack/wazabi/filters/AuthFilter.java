package ovh.gorillahack.wazabi.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * NOTE: ADDING API!!!
 */
@WebFilter(urlPatterns="/app/*")
public class AuthFilter implements Filter {

	protected FilterConfig config;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Authentication check

		HttpServletRequest requ = (HttpServletRequest) request;
		HttpServletResponse resp =  (HttpServletResponse) response;
		
		 Boolean session = (Boolean)  requ.getSession().getAttribute("authentificated");
		
		 if(session != null && (Boolean) session){
			 chain.doFilter(request, response);
			 return;
		 }
		 resp.sendRedirect(requ.getContextPath()+"/index.html");
		 
		
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	public void destroy() {}

}
