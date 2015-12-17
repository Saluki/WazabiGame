package ovh.gorillahack.wazabi.api;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.exception.NotEnoughDiceException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

@WebServlet("/api/game/givedices")
public class GameGiveDices extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private GestionPartie gestionPartie;
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject jsonResponse = new JSONObject();
		Joueur joueur = (Joueur) request.getSession().getAttribute("authenticated");
		
		int[] adversaires = this.getChallengerId(request);
		
		if( adversaires==null ) {
			
			jsonResponse.put("status", false);
			jsonResponse.put("message", "La liste des adversaires est inexistante");
			jsonResponse.put("debug", request.getParameterNames());
			
			response.getWriter().print(jsonResponse.toString());
			return;
		}
		
		try {
			
			gestionPartie.donnerDes(joueur, adversaires);
			jsonResponse.put("status", true);
			
		} catch (NotEnoughDiceException e) {
			jsonResponse.put("status", false);
			jsonResponse.put("message", "Pas assez de des pour pouvoir donner aux adversaires");
		}
		
		response.getWriter().println(jsonResponse.toJSONString());
	}
	
	protected int[] getChallengerId(HttpServletRequest request) {
	
		String[] stringIDs = request.getParameter("challengers").split(" ");
		
		if( stringIDs==null ) {
			return null;
		}
		
		int[] challengerIDs = new int[stringIDs.length];
		
		for(int i=0; i<challengerIDs.length; i++) {
			challengerIDs[i] = Integer.parseInt(stringIDs[i]);
		}
		
		return challengerIDs;
	}

}
