package ovh.gorillahack.wazabi.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.usecases.GestionPartie;
import ovh.gorillahack.wazabi.utils.Utils;

@WebServlet(urlPatterns = "/auth.html")
public class Authentication extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ServletContext context;

	@EJB
	private GestionPartie gestionPartie;

	public void init() {
		this.context = getServletContext();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect("index.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pseudo = request.getParameter("pseudo");
		String password = request.getParameter("mot_de_passe");

		if (!Utils.checkString(pseudo)) {
			redirectWithError(request, response, "Format du pseudo invalide");
			return;
		}

		if (!Utils.checkString(password)) {
			redirectWithError(request, response, "Format du mot de passe invalide");
			return;
		}
		Joueur joueur = gestionPartie.seConnecter(pseudo, password);
		if ( joueur != null) {
			request.getSession().setAttribute("authentificated", joueur);
			response.sendRedirect("app/dashboard.html");
			return;
		}

		redirectWithError(request, response, "Pseudo ou mot de passe incorrect");
	}

	protected void redirectWithError(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {

		request.setAttribute("errorMessage", message);
		context.getNamedDispatcher("index").forward(request, response);
	}

}
