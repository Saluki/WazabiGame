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

@WebServlet(urlPatterns = "/register.html")
public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@EJB
	private MockInterfaceGestionPartie gestionPartie;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			final ServletContext ctx = getServletContext();
			ctx.getNamedDispatcher("register").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Register and authenticate new user
		String pseudo = request.getParameter("pseudo");
		String mdp = request.getParameter("mot_de_passe");
		String mdpRepeat = request.getParameter("mot_de_passe_repeat");	
		final ServletContext ctx = getServletContext();
		synchronized (ctx) {
		// mdp et repeat mdp incorrecte et pseudo non existant
			if(mdp.equals(mdpRepeat) ){
				if( gestionPartie.verificationPseudo(pseudo)){
					gestionPartie.inscription(pseudo,mdp);
				
					ctx.getNamedDispatcher("app.dashboard").forward(request,response);
				}else{
					request.setAttribute("errorMessage", "Les deux mots de passe ne sont pas similaire");
					ctx.getNamedDispatcher("register").forward(request,response);
					return;
				}
				
			}else{
				request.setAttribute("errorMessage", "Le pseudo est deja utilise");
				ctx.getNamedDispatcher("register").forward(request,response);
				return;
			}
		
		}
		
	}

}
