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
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;
import ovh.gorillahack.wazabi.exception.PlayerNotFoundException;
import ovh.gorillahack.wazabi.exception.ValidationException;
import ovh.gorillahack.wazabi.exception.XmlParsingException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

@WebServlet(urlPatterns ="/app/game.html")
public class JoinGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	@EJB
	private GestionPartie  gestionPartie ;
	
	/**
	 * description : permet au joueur de rejoindre la partie 
	 * redirection : Si une partie est en cours, il est redirig� vers le dashboard.Si la partie est en attente de joueur, 
	 * il est redirige vers le jeux . Si aucune partie n'a ete creer et que tout les parties sont fini, il est redirige vers la 
	 * page lui permettant de creer une partie
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Joueur joueur = (Joueur)request.getSession().getAttribute("authenticated");
		Partie partie = null;
		try {
			partie = gestionPartie.rejoindrePartie(joueur);
		} catch (NoCurrentGameException | PlayerNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(partie == null)
			getServletContext().getNamedDispatcher("app.create").forward(request, response);
		else if(partie.getStatut().equals(Partie.Status.COMMENCE))
				response.sendRedirect(request.getContextPath() +"/app/dashboard.html");
		else 
			response.sendRedirect(request.getContextPath() +"/app/game.html");
	}

	/**
	 * description : re�oit un nom de partie en param�tre et le transfert a l'EJB pour creer une partie dans la base de donn�e
	 * exception : une ValidationException est lanc� si le nom de la partie n'est pas de type String
	 * redirection : Si la partie a bien �t� cr�e , la redirection est faite vers le jeux . Sinon la 
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
				redirectWithError(request, response, e.getMessage());
			} catch (XmlParsingException e) {
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
