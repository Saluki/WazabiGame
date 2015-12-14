package ovh.gorillahack.wazabi.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import ipl.mock.MockGestionPartie;
import ovh.gorillahack.wazabi.usecases.GestionPartie;
import ovh.gorillahack.wazabi.usecases.GestionPartieImpl;

@WebServlet(urlPatterns = "/api/game/status")
public class GameStatus extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	@EJB
	private MockGestionPartie gestionPartie ;
	// Developer notice
	// [java.lang.NoClassDefFoundError: org/json/simple/JSONObject]
	// => Use "Markers" > "Quick fix"
	
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		JSONObject statusObject = new JSONObject();
		
		// information a resortir de la base de donnée
		Stack pile = gestionPartie.pileInformation();
		//strucutre de la pile : 
		// 1)Map<String, String>
		Map<String,String> joueurInfo = (Map<String,String>)pile.pop();
		//2)Vector<Map<String, String>>
		Vector<Map<String, String>> carteInfo = (Vector<Map<String, String>>) pile.pop();
		//3)Vector<String>
		Vector<String> desJoueurs = (Vector<String>) pile.pop();

		//4)Vector<Map<String, Vector<String>>>
		Vector<Map<String, Vector<String>>> challengers = (Vector<Map<String, Vector<String>>>) pile.pop();
		
		// 1) Player informations
		JSONObject playerInfo = new JSONObject();
		playerInfo.putAll(joueurInfo);
		
		statusObject.put("player", playerInfo);
		
		// Personal game data
		JSONObject hand = new JSONObject();
		JSONObject cards = new JSONObject();
		for (int i = 0; i < carteInfo.size(); i++) {
			// carte
	
			Map<String,String> mapCarte = carteInfo.get(i);
			//ajout des cartes  dans la clé cards
			cards.putAll(mapCarte);
			
		}

		hand.put("cards", cards);
		
		// rajout des dés dans hand
		JSONObject des = new JSONObject();
		for (int i = 0; i < desJoueurs.size(); i++) {
			des.put("dices", desJoueurs.get(i));
		}
		
		//rajoute de hand dans la strucuture principale
		statusObject.put("hand", hand);

		
		
		
		// Challengers game data
		JSONObject challenger = new JSONObject();
		for (int i = 0; i < challengers.size(); i++) {
			Map<String, Vector<String>> mapChallenger = challengers.get(i);
			challenger.put("name",mapChallenger.get("name").get(0) );
			for (int j = 0; j < mapChallenger.get("dices").size(); j++) {
				challenger.put("dices",mapChallenger.get("dices").get(j) );
			}
			
		}
		statusObject.put("challengers", challenger);
		response.getWriter().print(statusObject.toJSONString());
	}
}
