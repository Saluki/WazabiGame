package ovh.gorillahack.wazabi.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ApiResponseFilter implements Filter {

	protected FilterConfig config;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setContentType("application/json");
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	public void destroy() {
	}

}
