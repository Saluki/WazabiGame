package ovh.gorillahack.wazabi.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Status;
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;
import ovh.gorillahack.wazabi.exception.ValidationException;
import ovh.gorillahack.wazabi.exception.XmlParsingException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

<<<<<<< HEAD
@WebServlet(urlPatterns = "/app/game.html")
=======
@WebServlet(urlPatterns ="/app/game.html")
>>>>>>> branch 'master' of https://github.com/Saluki/WazabiGame.git
public class JoinGame extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
<<<<<<< HEAD
	private GestionPartie gestionPartie;
=======
	private GestionPartie  gestionPartie ;
	
	/**
	 * description : permet au joueur de rejoindre la partie 
	 * redirection : Si une partie est en cours, il est redirigï¿½ vers le dashboard.Si la partie est en attente de joueur, 
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
>>>>>>> branch 'master' of https://github.com/Saluki/WazabiGame.git

	/**
<<<<<<< HEAD
	 * Permet au joueur de rejoindre la partie redirection.
=======
	 * description : reï¿½oit un nom de partie en paramï¿½tre et le transfert a l'EJB pour creer une partie dans la base de donnï¿½e
	 * exception : une ValidationException est lancï¿½ si le nom de la partie n'est pas de type String
	 * redirection : Si la partie a bien ï¿½tï¿½ crï¿½e , la redirection est faite vers le jeux . Sinon la 
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
>>>>>>> branch 'master' of https://github.com/Saluki/WazabiGame.git
	 * 
	 * Si une partie est en cours, il est redirigé vers le dashboard. Si la
	 * partie est en attente de joueur, il est redirige vers le jeux. Si aucune
	 * partie n'a ete creer et que tout les parties sont fini, il est redirige
	 * vers la page lui permettant de creer une partie.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Joueur joueur = (Joueur) request.getSession().getAttribute("authenticated");
		Partie partie;

		try {
			partie = gestionPartie.getPartieCourante();
		} catch (NoCurrentGameException e) {
			getServletContext().getNamedDispatcher("app.create").forward(request, response);
			return;
		}

		if (partie.getStatut() == Status.PAS_COMMENCE || partie.getStatut() == Status.ANNULEE) {
			getServletContext().getNamedDispatcher("app.create").forward(request, response);
			return;
		}

		if (partie.getStatut() == Status.COMMENCE) {
			// TODO Message d'explications
			response.sendRedirect(request.getContextPath() + "/app/dashboard.html");
			return;
		}

		// Statut "EN_ATTENTE"

		try {
			gestionPartie.rejoindrePartie(joueur);
		} catch (NoCurrentGameException e) {
			getServletContext().getNamedDispatcher("app.create").forward(request, response);
			return;
		}

		getServletContext().getNamedDispatcher("app.game").forward(request, response);
	}

	/**
	 * Reçoit un nom de partie en paramètre et le transfert a l'EJB pour creer
	 * une partie dans la base de donnée exception : une ValidationException est
	 * lancé si le nom de la partie n'est pas de type String redirection : Si la
	 * partie a bien été crée , la redirection est faite vers le jeux . Sinon la
	 * redirection est faite vers le dashboard.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Joueur joueur = (Joueur) request.getSession().getAttribute("authenticated");
		String nomPartie = request.getParameter("nom");
		
		synchronized (getServletContext()) {
			 
			try {
				
				Partie partieCourante = gestionPartie.getPartieCourante();
				
				if (partieCourante.getStatut() != Status.PAS_COMMENCE && partieCourante.getStatut() != Status.ANNULEE) {
					// TODO Message d'information
					response.sendRedirect(request.getContextPath() + "/app/dashboard.html");
					return;
				}
				
			} catch (NoCurrentGameException e) {}
			
			try {
				gestionPartie.creerPartie(nomPartie);
			} catch (ValidationException | XmlParsingException e) {
				// TODO Mettre message dans formulaire
				request.setAttribute("errorMessage", e.getMessage());
				getServletContext().getNamedDispatcher("app.create").forward(request, response);
				return;
			}
		}
			
		try {
			gestionPartie.rejoindrePartie(joueur);
		} catch (NoCurrentGameException e) {
			getServletContext().getNamedDispatcher("app.create").forward(request, response);
			return;
		}
		
		getServletContext().getNamedDispatcher("app.game").forward(request, response);
	}
}
