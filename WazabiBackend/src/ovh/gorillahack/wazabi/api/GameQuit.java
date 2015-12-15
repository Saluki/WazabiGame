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

/**
 * Servlet implementation class GameQuit
 */
@WebServlet("/api/game/quit")
public class GameQuit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       @EJB
       private GestionPartie gestionPartie;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO faire appelle a la GestionPartie pour le status
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("status", true);
		response.getWriter().println(jsonResponse.toJSONString());
	}


}
