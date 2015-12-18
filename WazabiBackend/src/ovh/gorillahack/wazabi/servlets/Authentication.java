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

@WebServlet(urlPatterns = "/auth.html")
public class Authentication extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ServletContext context;

	@EJB
	private GestionPartie gestionPartie;

	public void init() {
		this.context = getServletContext();
	}

	/**
	 * Redirection vers la page index.html
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect("index.html");
	}

	/**
	 * Recuperation des paremetres pseudo et mot_de_passe
	 * 
	 * Ceci afin de verifier si l'utilisateur a entre le bon mdp et le bon
	 * pseudo . Si oui il est redirige vers le dashboard ,sinon il reste sur le
	 * page et un message d'erreur lui est affiche. Une "Validation"
	 * exception est lance si il utilise un pseudo de plus de 250 caracteres ou
	 * si il utilise des caracteres speciaux
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pseudo = request.getParameter("pseudo");
		String password = request.getParameter("mot_de_passe");

		Joueur joueur;
		try {
			synchronized (getServletContext()) {
				joueur = gestionPartie.seConnecter(pseudo, password);
			}
		} catch (ValidationException e) {
			redirectWithError(request, response, e.getMessage());
			return;
		}
		if (joueur != null) {
			request.getSession().setAttribute("authenticated", joueur);
			response.sendRedirect("app/dashboard.html");
			return;
		}

		redirectWithError(request, response, "Pseudo ou mot de passe incorrect");
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
		context.getNamedDispatcher("index").forward(request, response);
	}

}
