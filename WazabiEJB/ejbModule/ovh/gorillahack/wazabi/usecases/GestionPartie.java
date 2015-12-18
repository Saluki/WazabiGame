
package ovh.gorillahack.wazabi.usecases;

import java.util.List;

import javax.ejb.Remote;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardNotFoundException;
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;
import ovh.gorillahack.wazabi.exception.NotEnoughDiceException;
import ovh.gorillahack.wazabi.exception.PlayerNotFoundException;
import ovh.gorillahack.wazabi.exception.XmlParsingException;

@Remote
public interface GestionPartie {

	/**
	 * Permet d'inscrire un joueur dans l'application.
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
	 * Permet de recuperer l'historique des parties d'un joueur auxquelles
	 * celui-ci a participe.
	 * 
	 * @param Joueur
	 *            j: le joueur dont on veux afficher l'historique.
	 * @return
	 */
	public List<Partie> afficherHistorique(Joueur j);

	/**
	 * Permet à un utilisateur de se connecter sur l'application.
	 * 
	 * @param pseudo
	 *            Le pseudo à verifier.
	 * @param motdepasse
	 *            Le mot de passe à verifier.
	 * @return Le joueur si l'authentification a reussi, null sinon.
	 * @throws ovh.gorillahack.wazabi.exception.ValidationException
	 */
	public Joueur seConnecter(String pseudo, String mdp) throws ovh.gorillahack.wazabi.exception.ValidationException;

	/**
	 * Permet à un joueur rejoindre la partie en cours. Si cette partie est
	 * commencee, le joueur n'est pas réjouté à la partie et retournera NULL.
	 * 
	 * @param Joueur
	 *            j: le joueur voulant rejoindre la partie.
	 * @return La partie que viens de rejoindre le joueur.
	 */
	public void rejoindrePartie(Joueur j) throws NoCurrentGameException;

	/**
	 * Permet de récupérer la liste des joueurs de la partie courante.
	 * 
	 * @return La liste des joueurs de la partie courante.
	 */
	public List<Joueur> listerJoueurPartieCourante() throws NoCurrentGameException;

	/**
	 * Permet de lancer les des d'un joueur.
	 * 
	 * @param Joueur
	 *            j: Le joueur dont on veux lancer les dés.
	 * @return La liste des De que le joueur a obtenu.
	 */
	public List<De> lancerDes(Joueur j);

	/**
	 * Permet de récuperer la liste des dés d'un joueur.
	 * 
	 * @param Joueur
	 *            j: Le joueur dont on veux récupérer les dés.
	 * @return List<De> la liste des dés du joueur.
	 */
	public List<De> voirDes(Joueur j);

	/**
	 * Permet de récuperer la liste des cartes d'un joueur.
	 * 
	 * @param Joueur
	 *            j: Le joueur dont on veux récupérer les dés.
	 * @return List<De> la liste des cartes du joueur.
	 */
	public List<Carte> voirCartes(Joueur j);

	/**
	 * Permet de terminer le tour du joueur courant.
	 * 
	 */
	public void terminerTour() throws NoCurrentGameException;

	/**
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
	 * Permet de recuperer les adversaires du joueur courant.
	 * 
	 * @return la liste des adversaires.
	 */
	public List<Joueur> getAdversaires(Joueur j) throws NoCurrentGameException;

	/**
	 * Permet de récupérer le nombre de tours qu'un joueur doit passer.
	 * 
	 * @param j
	 *            Le joueur dont on veut recuperer le nombre de tours a passer.
	 * @return Le nombre de tours que le joueur doit passer.
	 */
	public int getNombreDeToursAPasser(Joueur j);

	/**
	 * Permet de recuperer le joueur courant de la partie courante.
	 * 
	 * @return Le joueur courant.
	 */
	public Joueur getJoueurCourant();

	/**
	 * Permet de recuperer la partie courante.
	 * 
	 * @return La partie courante.
	 */
	public Partie getPartieCourante() throws NoCurrentGameException;

	/**
	 * Permet de deconnecter un joueur de la partie courante.
	 * 
	 * @param j
	 *            Le joueur que l'on veut deconnecter.
	 */
	public void deconnecter(Joueur j);

	public int getMin_joueurs();

	public void setMin_joueurs(int min_joueurs);

	/**
	 * Permet de recuperer le nombre maximum de joueurs d'une partie.
	 * 
	 * @return Le nombre de joueur maximum.
	 */
	public int getMax_joueurs();

	public void setMax_joueurs(int max_joueurs);

	/**
	 * Permet de recuperer le nombre de cartes distribues au debut de partie a
	 * chaque joueur.
	 * 
	 * @return Le nombre de cartes distribues au debut de partie a chaque
	 *         joueur.
	 */
	public int getNbCartesParJoueurs();

	public void setNbCartesParJoueurs(int nbCartesParJoueurs);

	/**
	 * Permet de recuperer le nombre de cartes total d'une partie.
	 * 
	 * @return Le nombre de cartes total d'une partie.
	 */
	public int getNbCartesTotal();

	public void setNbCartesTotal(int nbCartesTotal);

	/**
	 * Permet de recuperer le nombre de des distribues au debut de partie a
	 * chaque joueur.
	 * 
	 * @return le nombre de des par joueur.
	 */
	public int getNbDesParJoueur();

	public void setNbDesParJoueur(int nbDesParJoueur);

	/**
	 * Permet de recuperer le nombre de des total d'une partie.
	 * 
	 * @return Le nombre de des total d'une partie.
	 */
	public int getNbDesTotal();

	public void setNbDesTotal(int nbDesTotal);

	/**
	 * 
	 * Permet d'utiliser une carte dont aucun input est requis
	 * 
	 * @param id_carte
	 *            l'id de la carte.
	 */
	public void utiliserCarte(int id_carte) throws CardNotFoundException;

	/**
	 * Permet d'utiliser une carte dont on doit choisir un joueur.
	 * 
	 * @param id_carte
	 *            l'id de la carte
	 * @param j
	 *            le joueur à qui les effets seront appliqués
	 * @throws PlayerNotFoundException
	 */
	public void utiliserCarte(int id_carte, Joueur j) throws CardNotFoundException;

	/**
	 * 
	 * Permet d'utiliser une carte qui modifiera le sens de la partie.
	 * 
	 * @param id_carte
	 *            L'id de la carte
	 * @param sens
	 *            Le sens
	 */
	public void utiliserCarte(int id_carte, Sens sens) throws CardNotFoundException;

	/**
	 * 
	 * Permet de distribuer des dés à d'autres joueurs.
	 * 
	 * @param j
	 *            Le joueur courant
	 * @param id_adversaires
	 *            le tableau contenant les id des adversaires dont on doit
	 *            distribuer les des.
	 * @throws PlayerNotFoundException
	 */
	public void donnerDes(Joueur j, int[] id_adversaires) throws NotEnoughDiceException;

	public Carte piocherUneCarte(Joueur joueur);

	public Carte remettreCarte(Joueur joueur, Carte carte);

	public boolean laisserTousAdversairesAvecDeuxCartes(Carte c);

	boolean laisserAdversaireAvecDeuxCartes(Carte c);

	Carte piocherUneCarteChezUnJoueur(Carte c);

	public boolean passerTour(Carte c, Joueur j);

	public void supprimerDe(Joueur joueur);

	/**
	 * Renvoie le joueur suivant l'actuel selon le sens donné, ne vérifie pas si
	 * celui-ci doit passer son tour.
	 * 
	 * @param actuel
	 * @param sens
	 * @return
	 */
	public Joueur getJoueurSuivant(Joueur actuel, Sens sens);

	public void setPioche(List<Carte> pioche);

	public void changementDeSens(Sens sens) throws NoCurrentGameException;

	/**
	 * Renvoie le nombre de dés wazabi que le joueur a actuellement. Le joueur
	 * ne doit pas spécifiquement être le joueur courant bien que ça n'ait pas
	 * d'intérêt si ce n'est pas le cas.
	 * 
	 * @param joueur
	 * @return
	 */
	public int getNbWazabi(Joueur joueur);

	public void donnerDes(Joueur j, int id_joueur);

	public void changementDeSens() throws NoCurrentGameException;
}
