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

@WebFilter(urlPatterns = { "/", "/index.html", "/register.html", "/auth.html" })
public class GuestFilter implements Filter {

	protected FilterConfig config;

	/**
	 * Redirige l'utilisateur authentifie vers la page index des joueurs
	 * authentifie. Si l'utilisateur n'est pas authentifie il peut atteindre la
	 * page demandee.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (httpRequest.getSession().getAttribute("authenticated") != null) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/app/dashboard.html");
			return;
		}

		chain.doFilter(request, response);
		return;
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	public void destroy() {
	}

}
