package ovh.gorillahack.wazabi.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.exception.PlayerNotFoundException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

@WebServlet(urlPatterns = "/app/scoreboard.html")
public class ScoreBoard extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@EJB
	private GestionPartie gestionPartie;
	/**
	 * description : Recupere la session de l'utilisateur et recupere l'historique de ses anciennes parties. 
	 * 				Mets en attribut de la requete la liste et la renvoi a app.scoreboard
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Joueur joueur = (Joueur) request.getSession().getAttribute("authentificated");
		
		List<Partie> listeHistorique = null;
		try {
			listeHistorique = gestionPartie.afficherHistorique(joueur);
		} catch (PlayerNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("listeHistorique", listeHistorique);
		
		getServletContext().getNamedDispatcher("app.scoreboard").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
