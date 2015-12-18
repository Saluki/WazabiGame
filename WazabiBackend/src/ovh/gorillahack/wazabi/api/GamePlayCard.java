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
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardNotFoundException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

@SuppressWarnings("unchecked")
@WebServlet("/api/game/playcard")
public class GamePlayCard extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String NOTHING_TYPE = "AUCUN";
	private static final String PLAYER_TYPE = "JOUEUR";
	private static final String DIRECTION_TYPE = "SENS";

	@EJB
	private GestionPartie gestionPartie;

	protected JSONObject jsonResponse;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		jsonResponse = new JSONObject();
		int cardId;

		try {
			cardId = Integer.parseInt(request.getParameter("cardId"));
		}
		catch(NumberFormatException e) {
			throwJsonError(response, "L'identifiant de la carte n'est pas valide");
			return;
		}
		
		String inputType = request.getParameter("inputType");
		if( inputType==null ) {
			inputType = NOTHING_TYPE;
		}
		
		String input = request.getParameter("inputData");
		
		try {
			
			if( inputType.equals(NOTHING_TYPE) ) {
				
				gestionPartie.utiliserCarte(cardId);
				
			}
			else if( inputType.equals(PLAYER_TYPE) ) {
				
				int challengerId;
				try {
					challengerId = Integer.parseInt(input);
				}
				catch(NumberFormatException e) {
					throwJsonError(response, "Le numero de l'adversaire n'est pas valide");
					return;
				}
				
				Joueur challenger = gestionPartie.getPlayerFromId(challengerId);
				if( challenger==null ) {
					throwJsonError(response, "L'adversaire n'existe pas dans la partie");
					return;
				}
				
				gestionPartie.utiliserCarte(cardId, challenger);
			
			}
			else if( inputType.equals(DIRECTION_TYPE) ) {
				
				if( input.equals(Sens.HORAIRE) ) {
					gestionPartie.utiliserCarte(cardId, Sens.HORAIRE);
				}
				else if( input.equals(Sens.ANTIHORAIRE) ) {
					gestionPartie.utiliserCarte(cardId, Sens.ANTIHORAIRE);
				}
				else {
					throwJsonError(response, "Le sens \""+input + "\" n'est pas valide");
					return;
				}
				
			}
			else {
				
				throwJsonError(response, "Le type de carte n'est pas reconnu");
				return;
			}

		} 
		catch (CardNotFoundException e) {
			throwJsonError(response, "La carte n'a pas ete trouvee");
			return;
		}

		jsonResponse.put("succeed", true);
		jsonResponse.put("message", "Yeah, la carte a bien ete jouee!");
		
		response.getWriter().println(jsonResponse.toJSONString());
	}

	protected void throwJsonError(HttpServletResponse response, String message) throws IOException {

		jsonResponse.put("succeed", false);
		jsonResponse.put("message", message);
		response.getWriter().println(jsonResponse.toJSONString());
	}

}
