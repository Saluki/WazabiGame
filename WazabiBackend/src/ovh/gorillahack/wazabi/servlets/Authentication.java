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

@WebServlet(urlPatterns = "/auth.html")
public class Authentication extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@EJB
	private MockInterfaceGestionPartie gestionPartie;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
		// TODO Send back to authentication form
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Authenticate current user
		
		String pseudo = request.getParameter("pseudo");
		String mdp = request.getParameter("mot_de_passe");
		
		ServletContext ctx = getServletContext();
		if(gestionPartie.verificationAuthentification(pseudo,mdp)){
			request.getSession().setAttribute("authentificated", true);
			ctx.getNamedDispatcher("app.dashboard").forward(request, response);
			return;
		}
		request.setAttribute("errorMessage", "Authentification failed");
		ctx.getNamedDispatcher("index").forward(request, response);
	}

}
