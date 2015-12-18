package ovh.gorillahack.wazabi.api;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

@SuppressWarnings("unchecked")
@WebServlet("/api/game/quit")
public class GameQuit extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private GestionPartie gestionPartie;

	/**
	 * Methode REST permettant a un utilisateur de quitter une partie en cours.
	 * 
	 * L'utilisateur est deconnecte de la partie, le statut de la partie est mis
	 * a jour et un message de confirmation JSON est envoye a l'utilisateur.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject jsonResponse = new JSONObject();
		Joueur joueurConnecte = (Joueur) request.getSession().getAttribute("authenticated");

		synchronized (getServletContext()) {
			
			gestionPartie.deconnecter(joueurConnecte);
		}

		jsonResponse.put("status", true);

		response.getWriter().print(jsonResponse.toJSONString());
	}

}
