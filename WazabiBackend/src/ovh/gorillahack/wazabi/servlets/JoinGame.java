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

@WebServlet(urlPatterns = "/app/game.html")
public class JoinGame extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private GestionPartie gestionPartie;

	/**
	 * Permet au joueur de rejoindre la partie redirection.
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
