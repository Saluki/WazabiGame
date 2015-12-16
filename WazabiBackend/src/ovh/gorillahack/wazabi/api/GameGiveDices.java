package ovh.gorillahack.wazabi.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet("/api/game/givedices")
public class GameGiveDices extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO faire appelle a la GestionPartie pour le des
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("status", true);
		
		response.getWriter().println(jsonResponse.toJSONString());
	}

}
