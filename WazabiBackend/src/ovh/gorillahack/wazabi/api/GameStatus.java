package ovh.gorillahack.wazabi.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet(urlPatterns = "/api/game/status")
public class GameStatus extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Developer notice
	// [java.lang.NoClassDefFoundError: org/json/simple/JSONObject]
	// => Use "Markers" > "Quick fix"
	
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		JSONObject statusObject = new JSONObject();
		
		// Player informations
		JSONObject playerInfo = new JSONObject();
		playerInfo.put("pseudo", "John Doe");
		playerInfo.put("isCurrentTurn", true);
		statusObject.put("player", playerInfo);
		
		// Personal game data
		JSONObject gameData = new JSONObject();
		gameData.put("cards", "{}");
		gameData.put("dices", "{}");
		statusObject.put("personalGame", gameData);
		
		// Challengers game data
		statusObject.put("challengersGame", "[]");

		response.getWriter().print(statusObject.toJSONString());
	}
}
