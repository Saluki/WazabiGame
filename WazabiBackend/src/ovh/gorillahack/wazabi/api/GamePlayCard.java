package ovh.gorillahack.wazabi.api;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import ipl.mock.MockInterfaceGestionPartie;

@WebServlet("/api/game/playcard/*")
public class GamePlayCard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	@EJB
	private MockInterfaceGestionPartie gestionPartie;
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Return JSON dices result as an array
		int carte = Integer.parseInt(request.getPathInfo().substring(1));
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.putAll(gestionPartie.carteJouer(carte));
		
		response.getWriter().println(jsonResponse.toJSONString());
	}

}
