package ovh.gorillahack.wazabi.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/app/logout.html")
public class Logout extends HttpServlet {

	private static final long serialVersionUID = 1L;
/**
 * description: desiditentifie l'utilisateur, en rendant sa session invalide et en lui donnant une nouvelle.
 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getSession().invalidate();
		request.getSession();
		response.sendRedirect(request.getContextPath() +"/index.html");
		
	}

}
