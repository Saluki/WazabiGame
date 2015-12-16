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

import ovh.gorillahack.wazabi.domaine.Joueur;

@WebFilter(urlPatterns = { "/app/*", "/api/*" })
public class AuthFilter implements Filter {

	protected FilterConfig config;
/**
 * 
 * description : Verifie que l'utilisateur est bien authentifié pour acceder aux pages protégées
 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (httpRequest.getSession().getAttribute("authenticated") != null) {
			chain.doFilter(request, response);
			return;
		}

		httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.html");
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	public void destroy() {
	}

}
