
package ovh.gorillahack.wazabi.usecases;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.domaine.Partie.Status;

@Remote
public interface GestionPartie {

	/**
	 * 
	 * @param pseudo
	 *            Le pseudo du compte qu'on souhaite cr�er.
	 * @param motdepasse
	 *            Le mot de passe du compte qu'on souhaite cr�er.
	 * @return Le joueur cr�� si l'inscription a r�ussi, null si l'inscription a
	 *         �chou� (ex : pseudo existe d�j�).
	 */
	public Joueur inscrire(String pseudo, String motdepasse);
	
	/**
	 * 
	 * Permet de r�cup�rer l'historique des parties d'un joueur auquelles celui-ci a
	 * particip�. 
	 * 
	 * @param Joueur j: le joueur dont on veux afficher l'historique.
	 * @return
	 */
	public List<Partie> afficherHistorique(Joueur j);
	
	/**
	 * 
	 * Permet � un utilisateur de se connecter sur l'application. 
	 * 
	 * @param pseudo
	 *            Le pseudo � v�rifier.
	 * @param motdepasse
	 *            Le mot de passe � v�rifier.
	 * @return Le joueur si l'authentification a r�ussi, null sinon.
	 */
	public Joueur seConnecter(String pseudo, String mdp);
	
	/**
	 * 
	 * Permet � un joueur rejoindre la partie en cours. Si cette partie est commenc�e, 
	 * le joueur n'est pas r�jout� � la partie et retournera NULL.
	 * 
	 * @param Joueur j: le joueur voulant rejoindre la partie.
	 * @return La partie que viens de rejoindre le joueur.
	 */
	public Partie rejoindrePartie(Joueur j);
	
	
	/**
	 * 
	 * Permet de r�cup�rer la liste des joueurs de la partie courante.
	 * 
	 * @return La liste des joueurs de la partie courante.
	 */
	public List<Joueur> listerJoueurPartieCourante();
	
	/**
	 * 
	 * Permet de commencer la partie courante si le nombre de joueur minimum est atteint.
	 * 
	 * @return Le Joueur qui commencera la partie.
	 */
	public Joueur commencerPartie();
	
	/**
	 * 
	 * Permet de lancer les des d'un joueur.
	 * 
	 * @param Joueur j: Le joueur dont on veux lancer les d�s.
	 * @return La liste des De que le joueur a obtenu.
	 */
	public List<De> lancerDes(Joueur j);
	
    /**
     * 
     * Permet de r�cuperer la liste des d�s d'un joueur.
     * 
     * @param Joueur j: Le joueur dont on veux r�cup�rer les d�s.
     * @return List<De> la liste des d�s du joueur.
     */
	public List<De> voirDes(Joueur j);
	
	/**
	 * 
	 * Permet � un joueur de piocher une carte provenant de la pile.
	 * 
	 * @param Joueur j: le joueur qui doit piocher la carte.
	 * @return La carte que le joueur a piocher.
	 */
	public Carte piocherUneCarte(Joueur j);
	
	/**
	 * 
	 * Permet de terminer le tour du joueur courant.
	 * 
	 */
	public void terminerTour();
	
	/**
	 * 
	 * Permet de creer une partie selon le nom passe en parametre
	 * 
	 * @param Le nom de la partie
	 * @return La partie ainsi creee
	 */
	public Partie creerPartie(String nom);
}
