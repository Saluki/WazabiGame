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
	 * Permet au joueur de rejoindre la partie en attente ou d'en creer une.
	 * 
	 * Si une partie est en cours, il est redirige vers le dashboard. Si la
	 * partie est en attente de joueur, il est redirige vers le jeux. Si aucune
	 * partie n'a ete cree et que toutes les parties sont finies, il est
	 * redirige vers la page lui permettant de creer une partie.
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
			
			try {
				
				if( gestionPartie.listerJoueurPartieCourante().contains(joueur) ) {
					
					getServletContext().getNamedDispatcher("app.game").forward(request, response);
					return;
				}
			
			} catch (NoCurrentGameException e) {
				getServletContext().getNamedDispatcher("app.create").forward(request, response);
				return;
			}
			
			request.setAttribute("errorMessage", "Une partie est deja en cours. Veillez patienter...");
			getServletContext().getNamedDispatcher("app.create").forward(request, response);
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
	 * Recoit un nom de partie en parametre et le transfert a l'EJB pour creer
	 * une partie dans la base de donnee exception : une ValidationException est
	 * lance si le nom de la partie n'est pas de type String redirection : Si la
	 * partie a bien ete cree , la redirection est faite vers le jeux . Sinon la
	 * redirection est faite vers le dashboard.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Joueur joueur = (Joueur) request.getSession().getAttribute("authenticated");
		String nomPartie = request.getParameter("nom");

		try {

			synchronized (getServletContext()) {

				Partie partieCourante = gestionPartie.getPartieCourante();

				if (partieCourante.getStatut() == Status.EN_ATTENTE) {
					response.sendRedirect(request.getContextPath() + "/app/game.html");
					return;
				}

				if (partieCourante.getStatut() == Status.COMMENCE) {
					request.setAttribute("errorMessage", "Une partie est deja en cours. Veillez patienter...");
					getServletContext().getNamedDispatcher("app.create").forward(request, response);
					return;
				}
			}

		} catch (NoCurrentGameException e) {
			// We will create a new game
		}

		try {
			gestionPartie.creerPartie(nomPartie);
		} catch (ValidationException | XmlParsingException e) {
			request.setAttribute("errorMessage", e.getMessage());
			getServletContext().getNamedDispatcher("app.create").forward(request, response);
			return;
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
