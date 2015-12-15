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

import ipl.mock.MockInterfaceGestionPartie;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;

@WebServlet("/api/game/rolldices")
public class GameRollDices extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private MockInterfaceGestionPartie gestionPartie;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Joueur joueur = (Joueur) request.getAttribute("authentificated");
		List<De> listeDes = gestionPartie.lancerDes(joueur);
		
		response.getWriter().print(getJsonResponse(listeDes));
	}
	
	@SuppressWarnings("unchecked")
	protected String getJsonResponse(List<De> listeDes) {
		
		JSONArray dices = new JSONArray();
		for (De de : listeDes) {
			dices.add( de.getValeur().toString() );
		}
		JSONObject diceObject = new JSONObject();
		diceObject.put("dices", dices);
		
		return diceObject.toJSONString();
	}
}
