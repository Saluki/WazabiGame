package ovh.gorillahack.wazabi.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet("/api/game/playcard/*")
public class GamePlayCard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Return JSON dices result as an array
		
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("key", "value");
		
		response.getWriter().println(jsonResponse.toJSONString());
	}

}
