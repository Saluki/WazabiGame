package ovh.gorillahack.wazabi.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;
import ovh.gorillahack.wazabi.exception.PlayerNotFoundException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

@WebServlet(urlPatterns = "/api/game/status")
public class GameStatus extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@EJB
	private GestionPartie gestionPartie;
	// Developer notice
	// [java.lang.NoClassDefFoundError: org/json/simple/JSONObject]
	// => Use "Markers" > "Quick fix"

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Objet principal
		JSONObject statusObject = new JSONObject();

		// CHARGEMENT DES INFOS DU JOUEUR
		Joueur j = (Joueur) request.getSession().getAttribute("authentificated");
		JSONObject playerInfo = new JSONObject();
		// nom
		playerInfo.put("name", j.getPseudo());
		// play
		if (gestionPartie.getJoueurCourant().equals(j))
			playerInfo.put("play", true);
		else
			playerInfo.put("play", false);
		// mise en place dans l'objet principal
		statusObject.put("player", playerInfo);

		// CHARGEMENT DE LA MAIN DES JOUEURS
		// creation de l'objet hand
		JSONObject hand = new JSONObject();
		// creation de l'objet cards contenu dans hand
		JSONArray cards = new JSONArray();
		// chargement des cartes dans cards
		List<Carte> listeCarteJoueur = null;
		try {
			listeCarteJoueur = gestionPartie.voirCartes(j);
		} catch (PlayerNotFoundException e) {
			// TODO renvoye vers le dashboard
		}
		for (Carte carte : listeCarteJoueur) {
			JSONObject card = new JSONObject();
			card.put("name", carte.getEffet());
			card.put("description", carte.getDescription());
			card.put("effect", carte.getCodeEffet());
			card.put("cost", carte.getCout());
			card.put("input", carte.getInput());
			cards.add(card);
		}
		// chargement de cards dans hand
		hand.put("cards", cards);
		// CHARGEMENT DES DES DU JOUEURS
		JSONArray des = new JSONArray();
		List<De> listeDes = null;
		try {
			listeDes = gestionPartie.voirDes(j);
		} catch (PlayerNotFoundException e) {
			// TODO renvoye vers le dashboard
		}
		for (De de : listeDes) {
			des.add(de.getValeur().name());
		}
		// chargement des des du joueur dans la hand
		hand.put("dices", des);
		statusObject.put("hand", hand);
		// CHARGEMENT DES CHALLENGERS
		// declaration de l'objet challengers contenu dans statusGame
		JSONArray challengers = new JSONArray();
		List<Joueur> listJoueur = null;
		try {
			listJoueur = gestionPartie.listerJoueurPartieCourante();
		} catch (NoCurrentGameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Joueur jou : listJoueur) {
			if (jou.equals(j))
				continue;
			JSONObject challenger = new JSONObject();
			JSONArray dices = new JSONArray();
			List<De> deJoueur = null;
			try {
				deJoueur = gestionPartie.voirDes(jou);
			} catch (PlayerNotFoundException e) {
				// TODO retour vers dashboard
			}
			for (De de : deJoueur) {
					dices.add(de.getValeur().name());
			}
			challenger.put("name",jou.getPseudo());
			challenger.put("dices" , dices);
			challengers.add(challenger);

		}
		/*
		 * JSONObject statusObject = new JSONObject();
		 * 
		 * // information a resortir de la base de donnée Stack pile =
		 * gestionPartie.pileInformation(); // strucutre de la pile : //
		 * 4)Vector<Map<String, Vector<String>>> Vector<Map<String,
		 * Vector<String>>> challengerInfo = (Vector<Map<String,
		 * Vector<String>>>) pile.pop(); // 3)Vector<String> Vector<String>
		 * desJoueurs = (Vector<String>) pile.pop(); // 2)Vector<Map<String,
		 * String>> Vector<Map<String, String>> carteInfo = (Vector<Map<String,
		 * String>>) pile.pop(); // 1)Map<String, String> Map<String, String>
		 * joueurInfo = (Map<String, String>) pile.pop();
		 * 
		 * JSONObject playerInfo = new JSONObject();
		 * playerInfo.putAll(joueurInfo);
		 * 
		 * statusObject.put("player", playerInfo);
		 * 
		 * // Personal game data JSONObject hand = new JSONObject(); JSONArray
		 * cards = new JSONArray(); for (int i = 0; i < carteInfo.size(); i++) {
		 * // carte JSONObject card = new JSONObject(); Map<String, String>
		 * mapCarte = carteInfo.get(i); // ajout des cartes dans la clé cards
		 * card.putAll(mapCarte); cards.add(card);
		 * 
		 * }
		 * 
		 * hand.put("cards", cards);
		 * 
		 * // rajout des dés dans hand JSONArray des = new JSONArray(); for (int
		 * i = 0; i < desJoueurs.size(); i++) { des.add(desJoueurs.get(i)); }
		 * hand.put("dices", des); // rajoute de hand dans la strucuture
		 * principale statusObject.put("hand", hand);
		 * 
		 * // Challengers game data
		 * 
		 * JSONArray challengers = new JSONArray();
		 * 
		 * for (int i = 0; i < challengerInfo.size(); i++) { JSONObject
		 * challenger = new JSONObject(); JSONArray desChallenger = new
		 * JSONArray(); Map<String, Vector<String>> mapChallenger =
		 * challengerInfo.get(i); challenger.put("name",
		 * mapChallenger.get("name").get(0)); challenger.put("id",
		 * mapChallenger.get("id").get(0)); for (int j = 0; j <
		 * mapChallenger.get("dices").size(); j++) {
		 * desChallenger.add(mapChallenger.get("dices").get(j)); }
		 * challenger.put("dices", desChallenger);
		 * 
		 * challengers.add(challenger); } statusObject.put("challengers",
		 * challengers);
		 * response.getWriter().print(statusObject.toJSONString());
		 */
	}
}
