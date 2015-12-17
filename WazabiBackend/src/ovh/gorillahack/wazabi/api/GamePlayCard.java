package ovh.gorillahack.wazabi.api;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import ovh.gorillahack.wazabi.usecases.GestionPartie;

@SuppressWarnings("unchecked")
@WebServlet("/api/game/playcard")
public class GamePlayCard extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
			throwJsonError(response, "Le numero de carte n'est pas valide");
			return;
		}
		String input = request.getParameter("inputData");
		
		//try {
			
			if( input==null ) {
				
				// TODO De-mocker
				// gestionPartie.utiliserCarte(cardId);
				
			}
			else if( input.equals(PLAYER_TYPE) ) {
				
				// TODO Change method signature
				// gestionPartie.utiliserCarte(cardId);
			
			}
			else if( input.equals(DIRECTION_TYPE) ) {
				
				// TODO Change method signature
				// gestionPartie.utiliserCarte(cardId);
				
			}
			else {
				
				// Error!
			}

		/*} 
		catch (CardNotFoundException e) {
			throwJsonError(response, "La carte n'a pas ete trouvee");
			return;
		}*/

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
