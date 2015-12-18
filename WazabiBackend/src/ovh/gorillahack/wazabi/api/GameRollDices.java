package ovh.gorillahack.wazabi.api;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

@SuppressWarnings("unchecked")
@WebServlet("/api/game/rolldices")
public class GameRollDices extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private GestionPartie gestionPartie;

	/**
	 * Methode REST permettant de lancer et actualiser les des.
	 * 
	 * Les des sont lances pour le joueur en session et non le joueur courant.
	 * En lancant les des, les cartes dans le jeu du joueur en session seront
	 * aussi mises a jour en fonction des faces "cadeaux" des des.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Joueur joueur = (Joueur) request.getSession().getAttribute("authenticated");

		List<De> listeDes;
		synchronized (getServletContext()) {

			listeDes = gestionPartie.lancerDes(joueur);
		}

		response.getWriter().print(getJsonResponse(listeDes));
	}

	/**
	 * Genere et retourne une liste JSON contenant les valeurs des des.
	 * 
	 * La liste JSON est encapsulee dans un objet JSON. Cet objet prends la
	 * liste et la met en attribut avec la cle "dices".
	 * 
	 * @param listeDes
	 *            La liste de des a mettre dans la liste JSON
	 * @return Une liste JSON contenant les valeurs des des.
	 */
	protected String getJsonResponse(List<De> listeDes) {

		JSONArray dices = new JSONArray();
		for (De de : listeDes) {
			dices.add(de.getValeur().toString());
		}

		JSONObject diceObject = new JSONObject();
		diceObject.put("dices", dices);

		return diceObject.toJSONString();
	}
}
