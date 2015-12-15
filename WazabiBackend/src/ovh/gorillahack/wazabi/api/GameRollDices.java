package ovh.gorillahack.wazabi.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/api/game/rolldices")
public class GameRollDices extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO Return JSON dices result as an array
		
		JSONArray dices = new JSONArray();
		dices.add("C");
		dices.add("C");
		dices.add("A");
		
		JSONObject diceObject = new JSONObject();
		diceObject.put("dices", dices);
		
		response.getWriter().println(diceObject.toJSONString());
	}

}
