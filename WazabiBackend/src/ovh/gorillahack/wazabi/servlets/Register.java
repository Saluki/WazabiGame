package ovh.gorillahack.wazabi.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ipl.mock.MockInterfaceGestionPartie;
import ovh.gorillahack.wazabi.utils.Utils;

@WebServlet(urlPatterns = "/register.html")
public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private MockInterfaceGestionPartie gestionPartie;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final ServletContext context = getServletContext();
		context.getNamedDispatcher("register").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pseudo = request.getParameter("pseudo");
		String password = request.getParameter("mot_de_passe");
		String passwordRepeat = request.getParameter("mot_de_passe_repeat");
		
		if (!Utils.checkString(pseudo)) {
			redirectWithError(request, response, "Format du pseudo invalide");
			return;
		}
				
		if (!Utils.checkString(password)) {
			redirectWithError(request, response, "Format du mot de passe invalide");
			return;
		}
		
		if (!password.equals(passwordRepeat)) {
			redirectWithError(request, response, "Les deux mots de passe ne sont pas similaires");
			return;
		}
		
		final ServletContext context = getServletContext();
		synchronized (context) {

			if (gestionPartie.verificationPseudo(pseudo)) {
				
				// TODO Verifier si inscription reussie
				gestionPartie.inscription(pseudo, password);
				
				request.getSession().setAttribute("authentificated", true);
				response.sendRedirect("app/dashboard.html");
				return;
				
			} else {
				redirectWithError(request, response, "Le pseudo est deja utilise");
				return;
			}
		}
	}
	
	protected void redirectWithError(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {

		request.setAttribute("errorMessage", message);
		getServletContext().getNamedDispatcher("register").forward(request, response);
	}

}
