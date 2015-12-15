package ovh.gorillahack.wazabi.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

/**
 * Servlet implementation class JoinGame
 */
@WebServlet(urlPatterns ="/app/game.html")
public class JoinGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private GestionPartie  gestionPartie ;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Joueur joueur = (Joueur)request.getSession().getAttribute("authentificated");
		Partie partie = gestionPartie.rejoindrePartie(joueur);
		if(partie.getStatut().equals(Partie.Status.COMMENCE)){
				response.sendRedirect(request.getContextPath() +"/app/dashboard.html");
		}else if(partie.getStatut().equals(Partie.Status.EN_ATTENTE)){
			response.sendRedirect(request.getContextPath() +"/app/game.html");
		}else{
			getServletContext().getNamedDispatcher("app.create").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String partie = request.getParameter("nom");
		synchronized(getServletContext()){
			if( gestionPartie.creerPartie(partie) != null){
				getServletContext().getNamedDispatcher("app.game").forward(request, response);
				return;
			}else{
				request.setAttribute("message", "Une partie a été crée entre temps. Veuillez vous inscire a la partie");
				request.getServletContext().getNamedDispatcher("app.dashboard").forward(request, response);
				return;
			}
		}
	
	}

}
