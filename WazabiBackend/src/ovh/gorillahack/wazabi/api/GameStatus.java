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

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

@SuppressWarnings("unchecked")
@WebServlet(urlPatterns = "/api/game/status")
public class GameStatus extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private GestionPartie gestionPartie;

	/**
	 * L'objet JSON representant la reponse a renvoyer au client.
	 */
	protected JSONObject statusObject;

	/**
	 * Renvoie les informations de la en cours partie pour le joueur.
	 * 
	 * Les informations seront extraites pour le authentifie joueur se trouvant
	 * dans la session et seront renvoyes selon les conventions REST au format
	 * JSON.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		statusObject = new JSONObject();
		Joueur joueurSession = (Joueur) request.getSession().getAttribute("authenticated");

		try {

			addGameStatus();
			addPlayerData(joueurSession);
			addPlayerHand(joueurSession);
			addChallengersData(joueurSession);

		} catch (NoCurrentGameException e) {
			response.setStatus(500);
			return;
		}

		response.getWriter().print(statusObject.toJSONString());
	}

	/**
	 * Extrait l'etat du jeu pour le mettre dans la reponse JSON.
	 * 
	 * @throws NoCurrentGameException
	 */
	protected void addGameStatus() throws NoCurrentGameException {

		Partie partieCourante = gestionPartie.getPartieCourante();

		if (partieCourante.getVainqueur() != null) {
			statusObject.put("status", "TERMINE");
			return;
		}

		statusObject.put("status", partieCourante.getStatut().toString());
	}

	/**
	 * Ajoute les informations de base du joueur dans la reponse JSON.
	 * 
	 * Extrait le pseudo ainsi que le nombre de tours a passer pour les ajouter
	 * dans la reponse REST. Indique egalement a l'aide d'un booleen si c'est
	 * actuellement le tour du joueur qui se trouve dans la session.
	 * 
	 * @param joueurSession
	 *            Le joueur possedant la session
	 */
	protected void addPlayerData(Joueur joueurSession) {

		Joueur joueurCourant = gestionPartie.getJoueurCourant();

		JSONObject playerData = new JSONObject();
		playerData.put("name", joueurSession.getPseudo());
		playerData.put("play", joueurCourant.equals(joueurSession));
		playerData.put("skip", 0); // TODO Add number skipped

		statusObject.put("player", playerData);
	}

	/**
	 * Ajoute les cartes et les des du joueur a la reponse JSON.
	 * 
	 * Selectionne toutes les informations concernant les cartes jeu et les
	 * faces de des que le joueur possede dans sa "main".
	 */
	protected void addPlayerHand(Joueur joueurSession) {

		JSONArray cardsObject = new JSONArray();
		List<Carte> cardList = gestionPartie.voirCartes(joueurSession);
		for (Carte card : cardList) {

			JSONObject cardJsonObject = new JSONObject();
			cardJsonObject.put("id", card.getId_carte());
			cardJsonObject.put("name", card.getCarteEffet().getEffet());
			cardJsonObject.put("description", card.getDescription());
			cardJsonObject.put("effect", card.getCodeEffet());
			cardJsonObject.put("cost", card.getCout());
			cardJsonObject.put("input", card.getInput().toString());

			cardsObject.add(cardJsonObject);
		}

		JSONArray dicesArray = new JSONArray();
		List<De> playerDices = gestionPartie.voirDes(joueurSession);
		for (De de : playerDices) {
			dicesArray.add(de.getValeur().toString());
		}

		JSONObject playerHandObject = new JSONObject();
		playerHandObject.put("cards", cardsObject);
		playerHandObject.put("dices", dicesArray);

		statusObject.put("hand", playerHandObject);
	}

	/**
	 * Ajoute les donnees concernant les adversaires du joueur.
	 * 
	 * Pour chaque challenger, son nom, son ID, son nombre de cartes et sa
	 * combinaison de des, sont extraites et mis dans l'objet JSON representant
	 * la reponse REST.
	 * 
	 * @param joueurSession
	 *            Le joueur possedant la session
	 * @throws NoCurrentGameException
	 */
	protected void addChallengersData(Joueur joueurSession) throws NoCurrentGameException {

		JSONArray challengersList = new JSONArray();
		List<Joueur> challengers = gestionPartie.getAdversaires(joueurSession);

		for (Joueur challenger : challengers) {

			JSONObject challengerObject = new JSONObject();

			challengerObject.put("id", challenger.getId_joueur());
			challengerObject.put("name", challenger.getPseudo());

			JSONArray dicesArray = new JSONArray();
			List<De> challengerDices = gestionPartie.voirDes(challenger);
			for (De dice : challengerDices) {
				dicesArray.add(dice.getValeur().toString());
			}
			challengerObject.put("dices", dicesArray);

			int cardNumber = gestionPartie.voirCartes(challenger).size();
			challengerObject.put("cardnumber", cardNumber);

			challengersList.add(challengerObject);
		}

		statusObject.put("challengers", challengersList);
	}
}
