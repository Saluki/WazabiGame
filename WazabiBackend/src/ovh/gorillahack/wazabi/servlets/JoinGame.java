package ovh.gorillahack.wazabi.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.exception.ValidationException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;
import ovh.gorillahack.wazabi.util.Utils;



/**
 * @author jvandera15
 *
 */
@WebServlet(urlPatterns ="/app/game.html")
public class JoinGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	@EJB
	private GestionPartie  gestionPartie ;
	
	/**
	 * description : permet au joueur de rejoindre la partie 
	 * redirection : Si une partie est en cours, il est redirigé vers le dashboard.Si la partie est en attente de joueur, 
	 * il est redirige vers le jeux . Si aucune partie n'a ete creer et que tout les parties sont fini, il est redirige vers la 
	 * page lui permettant de creer une partie
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Joueur joueur = (Joueur)request.getSession().getAttribute("authentificated");
		Partie partie = gestionPartie.rejoindrePartie(joueur);
		if(partie == null)
			getServletContext().getNamedDispatcher("app.create").forward(request, response);
		else if(partie.getStatut().equals(Partie.Status.COMMENCE))
				response.sendRedirect(request.getContextPath() +"/app/dashboard.html");
		else 
			response.sendRedirect(request.getContextPath() +"/app/game.html");
	}

	/**
	 * description : reçoit un nom de partie en paramètre et le transfert a l'EJB pour creer une partie dans la base de donnée
	 * exception : une ValidationException est lancé si le nom de la partie n'est pas de type String
	 * redirection : Si la partie a bien été crée , la redirection est faite vers le jeux . Sinon la 
	 *  			redirection est faite vers le dashboard
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		context = getServletContext();
		String partie = request.getParameter("nom");

		
		synchronized(getServletContext()){
			try {
				if( gestionPartie.creerPartie(partie) != null){
					getServletContext().getNamedDispatcher("app.game").forward(request, response);
					return;
				}else{
					request.setAttribute("message", "Une partie a ete cree entre temps. Veuillez vous inscire a la partie");
					request.getServletContext().getNamedDispatcher("app.dashboard").forward(request, response);
					return;
				}
			} catch (ValidationException e) {
				// TODO Auto-generated catch block
				redirectWithError(request, response, e.getMessage());
			}
		}
	
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param message
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void redirectWithError(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {

		request.setAttribute("errorMessage", message);
		context.getNamedDispatcher("app.create").forward(request, response);
	}

}
