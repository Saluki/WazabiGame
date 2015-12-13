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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		JSONObject subObject = new JSONObject();
		subObject.put("lastKey", "value");
		
		JSONObject object = new JSONObject();
		object.put("key", "value");
		object.put("otherKey", subObject);
		
		// TODO Return game status as JSON object
		// http://www.json.org/
		// http://www.tutorialspoint.com/json/json_java_example.htm
		
		response.getWriter().print(object.toJSONString());
	}

}
