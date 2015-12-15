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

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Return JSON dices result as an array
		Joueur joueur = (Joueur) request.getAttribute("authentificated");
		List<De> listeDe = gestionPartie.lancerDes(joueur);
		JSONArray dices = new JSONArray();
		for (De de : listeDe) {
			dices.add(de.getValeur());
		}
		JSONObject diceObject = new JSONObject();
		diceObject.put("dices", dices);

		response.getWriter().println(diceObject.toJSONString());
	}

}
