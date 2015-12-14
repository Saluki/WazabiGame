package ovh.gorillahack.wazabi.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ipl.mock.MockInterfaceGestionPartie;

/**
 * Servlet implementation class CreatePartie
 */
@WebServlet(urlPatterns ="/app/create.html")
public class CreatePartie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private MockInterfaceGestionPartie gestionPartie;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	String partie = request.getParameter("nom");
	System.out.println("------------------------------------passer---------------------------------------------");
		synchronized(getServletContext()){
			
			if( gestionPartie.createPartie(partie)){
			
				response.sendRedirect(request.getContextPath() +"/app/game.html");
				return;
			}else{
				request.setAttribute("message", "Une partie a été crée entre temps. Veuillez vous inscire a la partie");
				request.getServletContext().getNamedDispatcher("app.dashboard").forward(request, response);
				return;
			}
		}
	
	}
	

}
