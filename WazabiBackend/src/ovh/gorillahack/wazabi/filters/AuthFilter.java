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
	 * Verifie que l'utilisateur est bien authentifie.
	 * 
	 * Ce filtre permet de limit l'acces au pages protegees par l'application.
	 * Si l'utilisateur n'est pas authentifie, il sera redirige vers la page de
	 * login pour se connecter.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		Object authObject = httpRequest.getSession().getAttribute("authenticated");

		if (authObject != null && authObject instanceof Joueur) {
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
