package ovh.gorillahack.wazabi.api;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ipl.mock.MockInterfaceGestionPartie;

@WebServlet(urlPatterns = "/api/game/status")
public class GameStatus extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@EJB
	private MockInterfaceGestionPartie gestionPartie;
	// Developer notice
	// [java.lang.NoClassDefFoundError: org/json/simple/JSONObject]
	// => Use "Markers" > "Quick fix"

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject statusObject = new JSONObject();

		// information a resortir de la base de donnée
		Stack pile = gestionPartie.pileInformation();
		// strucutre de la pile :
		// 4)Vector<Map<String, Vector<String>>>
		Vector<Map<String, Vector<String>>> challengerInfo = (Vector<Map<String, Vector<String>>>) pile.pop();
		// 3)Vector<String>
		Vector<String> desJoueurs = (Vector<String>) pile.pop();
		// 2)Vector<Map<String, String>>
		Vector<Map<String, String>> carteInfo = (Vector<Map<String, String>>) pile.pop();
		// 1)Map<String, String>
		Map<String, String> joueurInfo = (Map<String, String>) pile.pop();

		JSONObject playerInfo = new JSONObject();
		playerInfo.putAll(joueurInfo);

		statusObject.put("player", playerInfo);

		// Personal game data
		JSONObject hand = new JSONObject();
		JSONArray cards = new JSONArray();
		for (int i = 0; i < carteInfo.size(); i++) {
			// carte
			JSONObject card = new JSONObject();
			Map<String, String> mapCarte = carteInfo.get(i);
			// ajout des cartes dans la clé cards
			card.putAll(mapCarte);
			cards.add(card);

		}

		hand.put("cards", cards);

		// rajout des dés dans hand
		JSONArray des = new JSONArray();
		for (int i = 0; i < desJoueurs.size(); i++) {
			des.add(desJoueurs.get(i));
		}
		hand.put("dices", des);
		// rajoute de hand dans la strucuture principale
		statusObject.put("hand", hand);

		// Challengers game data

		JSONArray challengers = new JSONArray();
		
		for (int i = 0; i < challengerInfo.size(); i++) {
			JSONObject challenger = new JSONObject();
			JSONArray desChallenger = new JSONArray();
			Map<String, Vector<String>> mapChallenger = challengerInfo.get(i);
			challenger.put("name", mapChallenger.get("name").get(0));
			challenger.put("id", mapChallenger.get("id").get(0));
			for (int j = 0; j < mapChallenger.get("dices").size(); j++) {
				desChallenger.add(mapChallenger.get("dices").get(j));
			}
			challenger.put("dices", desChallenger);
			
			challengers.add(challenger);
		}
		statusObject.put("challengers", challengers);
		response.getWriter().print(statusObject.toJSONString());
	}
}
