
package ovh.gorillahack.wazabi.usecases;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.validation.ValidationException;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.domaine.Partie.Status;
import ovh.gorillahack.wazabi.exception.NotEnoughDiceException;
import ovh.gorillahack.wazabi.exception.XmlParsingException;

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
	 * @throws ovh.gorillahack.wazabi.exception.ValidationException
	 */
	public Joueur inscrire(String pseudo, String motdepasse, String motdepasseRepeat)
			throws ovh.gorillahack.wazabi.exception.ValidationException;

	/**
	 * 
	 * Permet de récupérer l'historique des parties d'un joueur auquelles
	 * celui-ci a participé.
	 * 
	 * @param Joueur
	 *            j: le joueur dont on veux afficher l'historique.
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
	 * @throws ovh.gorillahack.wazabi.exception.ValidationException
	 */
	public Joueur seConnecter(String pseudo, String mdp) throws ovh.gorillahack.wazabi.exception.ValidationException;

	/**
	 * 
	 * Permet à un joueur rejoindre la partie en cours. Si cette partie est
	 * commencée, le joueur n'est pas réjouté à la partie et retournera NULL.
	 * 
	 * @param Joueur
	 *            j: le joueur voulant rejoindre la partie.
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
	 * Permet de commencer la partie courante si le nombre de joueur minimum est
	 * atteint.
	 * 
	 * @return Le Joueur qui commencera la partie.
	 */
	public void commencerPartie();

	/**
	 * 
	 * Permet de lancer les des d'un joueur.
	 * 
	 * @param Joueur
	 *            j: Le joueur dont on veux lancer les dés.
	 * @return La liste des De que le joueur a obtenu.
	 */
	public List<De> lancerDes(Joueur j);

	/**
	 * 
	 * Permet de récuperer la liste des dés d'un joueur.
	 * 
	 * @param Joueur
	 *            j: Le joueur dont on veux récupérer les dés.
	 * @return List<De> la liste des dés du joueur.
	 */
	public List<De> voirDes(Joueur j);

	/**
	 * 
	 * Permet de récuperer la liste des cartes d'un joueur.
	 * 
	 * @param Joueur
	 *            j: Le joueur dont on veux récupérer les dés.
	 * @return List<De> la liste des cartes du joueur.
	 */
	public List<Carte> voirCartes(Joueur j);

	/**
	 * 
	 * Permet à un joueur de piocher une carte provenant de la pile.
	 * 
	 * @param Joueur
	 *            j: le joueur qui doit piocher la carte.
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
	 * @param nom
	 *            Le nom de la partie
	 * @return La partie ainsi creee
	 * @throws ovh.gorillahack.wazabi.exception.ValidationException
	 * @throws XmlParsingException
	 *             Si le parsing du XML a échoué.
	 */
	public Partie creerPartie(String nom)
			throws ovh.gorillahack.wazabi.exception.ValidationException, XmlParsingException;

	/**
	 * 
	 * Permet de recuperer les adversaires du joueur courant.
	 * 
	 * @return la liste des adversaires.
	 */
	public List<Joueur> getAdversaires(Joueur j);

	/**
	 * 
	 * Permet de recuperer le joueur courant de la partie courante.
	 * 
	 * @return Le joueur courant.
	 */
	public Joueur getJoueurCourant();

	/**
	 * 
	 * Permet de recuperer la partie courante.
	 * 
	 * @return La partie courante.
	 */
	public Partie getPartieCourante();

	public void deconnecter(Joueur j);

	public List<Carte> getJeuDeCarte();

	public void setJeuDeCarte(List<Carte> liste);

	public int getMin_joueurs();

	public void setMin_joueurs(int min_joueurs);

	public int getMax_joueurs();

	public void setMax_joueurs(int max_joueurs);

	public int getNbCartesParJoueurs();

	public void setNbCartesParJoueurs(int nbCartesParJoueurs);

	public int getNbCartesTotal();

	public void setNbCartesTotal(int nbCartesTotal);

	public int getNbDesParJoueur();

	public void setNbDesParJoueur(int nbDesParJoueur);

	public int getNbDesTotal();

	public void setNbDesTotal(int nbDesTotal);

	/**
	 * 
	 * Permet d'utiliser une carte dont aucun input est requis
	 * 
	 * @param id_carte
	 *            l'id de la cart
	 */
	public void utiliserCarte(int id_carte);

	/**
	 * Permet d'utiliser une carte dont on doit choisir un joueur.
	 * 
	 * @param id_carte
	 *            l'id de la carte
	 * @param j
	 *            le joueur à qui les effets seront appliqués
	 */
	public void utiliserCarte(int id_carte, Joueur j);

	/**
	 * 
	 * Permet d'utiliser une carte qui modifiera le sens de la partie.
	 * 
	 * @param id_carte
	 *            L'id de la carte
	 * @param sens
	 *            Le sens
	 */
	public void utiliserCarte(int id_carte, Sens sens);
	
	/**
	 * 
	 * Permet de distribuer des dés à d'autres joueurs.  
	 * 
	 * @param j Le joueur courant
	 * @param id_adversaires le tableau contenant les id des adversaires dont on doit distribuer les des.
	 */
	public void donnerDes(Joueur j, int[] id_adversaires) throws NotEnoughDiceException;

}
