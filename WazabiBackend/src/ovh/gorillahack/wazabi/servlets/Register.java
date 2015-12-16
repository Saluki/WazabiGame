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
import ovh.gorillahack.wazabi.exception.ValidationException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

@WebServlet(urlPatterns = "/register.html")
public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private GestionPartie gestionPartie;
/**
 * 
 * 
 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final ServletContext context = getServletContext();
		context.getNamedDispatcher("register").forward(request, response);
	}
/**
 * Parametre : recupere le pseudo, mdp et le mdp a retaper
 * description : Inscrit le nouvelle utilisateur dans la base de donn�e . Si l'inscription echoue , il reste sur la page
 *				et un message lui est indiquer . Sinon il est rediriger directement dans le dashboard
 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pseudo = request.getParameter("pseudo");
		String password = request.getParameter("mot_de_passe");
		String passwordRepeat = request.getParameter("mot_de_passe_repeat");

		final ServletContext context = getServletContext();
		synchronized (context) {
			Joueur joueur;
			try {
				joueur = gestionPartie.inscrire(pseudo, password, passwordRepeat);
			} catch (ValidationException e) {
				redirectWithError(request, response, e.getMessage());
				return;
			}
			if (joueur != null) {
				request.getSession().setAttribute("authenticated", joueur);
				response.sendRedirect("app/dashboard.html");
				return;

			} else {
				redirectWithError(request, response, "Le pseudo est deja utilise.");
				return;
			}
		}
	}
/**
 * 
 * @param request
 * @param response
 * @param message
 * @throws ServletException
 * @throws IOException
 */
	protected void redirectWithError(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {

		request.setAttribute("errorMessage", message);
		getServletContext().getNamedDispatcher("register").forward(request, response);
	}

}
