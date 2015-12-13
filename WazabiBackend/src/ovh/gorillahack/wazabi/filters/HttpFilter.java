package ovh.gorillahack.wazabi.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class HttpFilter implements Filter {

	protected FilterConfig config;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if( !( request instanceof HttpServletRequest ) ) {
			
			request.setAttribute("errorMessage", "Protocole HTTP requis");
			config.getServletContext().getNamedDispatcher("error.main").forward(request, response);
			return;
		}

		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	public void destroy() {}

}
