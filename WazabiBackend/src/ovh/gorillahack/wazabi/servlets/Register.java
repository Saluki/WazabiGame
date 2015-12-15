package ovh.gorillahack.wazabi.servlets;

import java.io.IOException;
import java.util.regex.Pattern;

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

@WebServlet(urlPatterns = "/register.html")
public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private GestionPartie gestionPartie;

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
		
		if (!Utils.checkString(pseudo) &&  Pattern.matches("([a-z]|[0-9]){1,20}", pseudo)) {
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
			Joueur joueur;
			try{
			 joueur = gestionPartie.inscrire(pseudo, password);
			}catch(Exception e){
				redirectWithError(request, response, "Format du pseudo invalide : Le pseudo ne peut depasser 20 caracteres.");
				return;
			}
		if ( joueur != null) {
				request.getSession().setAttribute("authentificated", joueur);
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
