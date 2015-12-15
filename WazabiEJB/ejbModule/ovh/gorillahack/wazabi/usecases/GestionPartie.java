
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
	 *            Le pseudo du compte qu'on souhaite créer.
	 * @param motdepasse
	 *            Le mot de passe du compte qu'on souhaite créer.
	 * @return Le joueur créé si l'inscription a réussi, null si l'inscription a
	 *         échoué (ex : pseudo existe déjà).
	 */
	public Joueur inscrire(String pseudo, String motdepasse);
	
	/**
	 * 
	 * Permet de récupérer l'historique des parties d'un joueur auquelles celui-ci a
	 * participé. 
	 * 
	 * @param Joueur j: le joueur dont on veux afficher l'historique.
	 * @return
	 */
	public List<Partie> afficherHistorique(Joueur j);
	
	/**
	 * 
	 * Permet à un utilisateur de se connecter sur l'application. 
	 * 
	 * @param pseudo
	 *            Le pseudo à vérifier.
	 * @param motdepasse
	 *            Le mot de passe à vérifier.
	 * @return Le joueur si l'authentification a réussi, null sinon.
	 */
	public Joueur seConnecter(String pseudo, String mdp);
	
	/**
	 * 
	 * Permet à un joueur rejoindre la partie en cours. Si cette partie est commencée, 
	 * le joueur n'est pas réjouté à la partie et retournera NULL.
	 * 
	 * @param Joueur j: le joueur voulant rejoindre la partie.
	 * @return La partie que viens de rejoindre le joueur.
	 */
	public Partie rejoindrePartie(Joueur j);
	
	
	/**
	 * 
	 * Permet de récupérer la liste des joueurs de la partie courante.
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
	 * @param Joueur j: Le joueur dont on veux lancer les dés.
	 * @return La liste des De que le joueur a obtenu.
	 */
	public List<De> lancerDes(Joueur j);
	
    /**
     * 
     * Permet de récuperer la liste des dés d'un joueur.
     * 
     * @param Joueur j: Le joueur dont on veux récupérer les dés.
     * @return List<De> la liste des dés du joueur.
     */
	public List<De> voirDes(Joueur j);
	
	/**
	 * 
	 * Permet à un joueur de piocher une carte provenant de la pile.
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
