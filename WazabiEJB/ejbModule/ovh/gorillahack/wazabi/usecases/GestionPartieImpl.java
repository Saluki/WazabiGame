package ovh.gorillahack.wazabi.usecases;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import ovh.gorillahack.wazabi.chain.GestionnaireCarte;
import ovh.gorillahack.wazabi.chain.GestionnaireCarteChangerSens;
import ovh.gorillahack.wazabi.chain.GestionnaireCarteDonnerDe;
import ovh.gorillahack.wazabi.chain.GestionnaireCarteEnleverDe;
import ovh.gorillahack.wazabi.chain.GestionnaireCarteLaisser2CartesAdversaires;
import ovh.gorillahack.wazabi.chain.GestionnaireCarteLaisserCarte;
import ovh.gorillahack.wazabi.chain.GestionnaireCartePasserTour;
import ovh.gorillahack.wazabi.chain.GestionnaireCartePioche3;
import ovh.gorillahack.wazabi.chain.GestionnaireCartePrendreCarte;
import ovh.gorillahack.wazabi.chain.GestionnaireCarteSupprimerDe;
import ovh.gorillahack.wazabi.chain.GestionnaireCarteTournerDe;
import ovh.gorillahack.wazabi.dao.CarteDaoImpl;
import ovh.gorillahack.wazabi.dao.DeDaoImpl;
import ovh.gorillahack.wazabi.dao.JoueurDaoImpl;
import ovh.gorillahack.wazabi.dao.JoueurPartieDaoImpl;
import ovh.gorillahack.wazabi.dao.PartieDaoImpl;
import ovh.gorillahack.wazabi.dao.XmlParserImpl;
import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Face.Valeur;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardConstraintViolatedException;
import ovh.gorillahack.wazabi.exception.CardNotFoundException;
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;
import ovh.gorillahack.wazabi.exception.NotEnoughDiceException;
import ovh.gorillahack.wazabi.exception.ValidationException;
import ovh.gorillahack.wazabi.exception.XmlParsingException;
import ovh.gorillahack.wazabi.util.Utils;

/**
 * Session Bean implementation class GestionPartieImpl
 */
@Singleton
@Startup
@Remote(GestionPartie.class)
public class GestionPartieImpl implements GestionPartie {
	private Partie partieCourante;
	private int min_joueurs;
	private int max_joueurs;
	private int nbCartesParJoueurs;
	private int nbCartesTotal;
	private int nbDesParJoueur;
	private int nbDesTotal;
	private List<Carte> pioche;
	private GestionnaireCarte gc;

	@EJB
	private JoueurDaoImpl joueurDaoImpl;

	@EJB
	private JoueurPartieDaoImpl joueurPartieDaoImpl;

	@EJB
	private PartieDaoImpl partieDaoImpl;

	@EJB
	private XmlParserImpl xmlParserImpl;

	@EJB
	private CarteDaoImpl carteDaoImpl;
	
	@EJB
	private DeDaoImpl deDaoImpl;

	@PostConstruct
	public void postconstruct() {
		System.out.println("GestionPartieImpl created");
		try {
			inscrire("em", "em", "em");
			inscrire("mi", "mi", "mi");
			inscrire("ol", "ol", "ol");
			this.gc = new GestionnaireCarteEnleverDe(new GestionnaireCarteTournerDe(new GestionnaireCarteSupprimerDe(
					new GestionnaireCarteDonnerDe(new GestionnaireCartePrendreCarte(new GestionnaireCarteLaisserCarte(
							new GestionnaireCartePioche3(new GestionnaireCarteLaisser2CartesAdversaires(
									new GestionnaireCartePasserTour(new GestionnaireCarteChangerSens(null))))))))));
		} catch (ValidationException e) {
		}
	}

	@PreDestroy
	public void predestroy() {
		System.out.println("GestionPartieImpl destroyed");
	}

	/**
	 * Default constructor.
	 */
	public GestionPartieImpl() {
		// TODO Lors de la selection du joueur courant, il faut prendre en
		// compte le champ "compteur_saut".
	}

	@Override
	public Joueur inscrire(String pseudo, String motdepasse, String motdepasseRepeat) throws ValidationException {
		if (!Utils.checkString(pseudo) || !Pattern.matches("[a-zA-Z0-9]{1,20}", pseudo)) {
			throw new ValidationException("Format du pseudo invalide .");
		}

		if (!Utils.checkString(motdepasse) || !Pattern.matches("[a-zA-Z0-9]{1,20}", motdepasse)) {
			throw new ValidationException("Format du mot de passe invalide.");
		}

		if (!motdepasse.equals(motdepasseRepeat) || !Pattern.matches("[a-zA-Z0-9]{1,20}", motdepasseRepeat)) {
			throw new ValidationException("Les deux mots de passe ne sont pas similaires.");
		}

		return joueurDaoImpl.inscrire(pseudo, motdepasse);
	}

	@Override
	public List<Partie> afficherHistorique(Joueur j) {
		List<Partie> listeRenv = partieDaoImpl.afficherHistorique(j);
		return listeRenv;
	}

	@Override
	public void rejoindrePartie(Joueur j) throws NoCurrentGameException {
		partieCourante = partieDaoImpl.rejoindrePartie(j);
		int nbJoueursSalon = listerJoueurPartieCourante().size();
		if (nbJoueursSalon >= min_joueurs)
			commencerPartie();
	}

	@Override
	public List<Joueur> listerJoueurPartieCourante() throws NoCurrentGameException {
		List<Joueur> listeRenv = partieDaoImpl.listerJoueurPartieCourante();
		if (listeRenv.isEmpty())
			throw new NoCurrentGameException("Aucune partie n'est en cours");
		return listeRenv;
	}

	@Override
	public List<Joueur> getAdversaires(Joueur j) throws NoCurrentGameException {
		List<Joueur> adversaires = listerJoueurPartieCourante();
		if (adversaires.isEmpty())
			throw new NoCurrentGameException("Aucune partie n'est en cours");
		adversaires.remove(j);
		return adversaires;
	}

	/**
	 * Permet de commencer la partie courante si le nombre de joueur minimum est
	 * atteint.
	 * 
	 * @return Le Joueur qui commencera la partie.
	 */
	private void commencerPartie() throws NoCurrentGameException {
		/*
		 * TODO faudra reinitialiser toutes les données si on veut pouvoir
		 * reutiliser le même paquet de cartes (ex id_joueur, id_partie, ...)
		 */
		partieDaoImpl.enregistrerPioche(pioche);
		partieCourante = partieDaoImpl.commencerPartie(nbCartesParJoueurs, nbDesParJoueur);
		if (partieCourante == null)
			throw new NoCurrentGameException("La partie n'a pas pu être lancé . Veuiller reesayer");
	}

	@Override
	public List<De> lancerDes(Joueur j) {
		List<De> listeRenv = joueurDaoImpl.lancerDes(j);
		for(int i = 0; i<listeRenv.size();i++){
			De d = listeRenv.get(i);
			if(d.getValeur()==Valeur.PIOCHE){
				piocherUneCarte(j);			
			}
		}
		return listeRenv;
	}

	@Override
	public List<De> voirDes(Joueur j) {
		List<De> listeRenv = joueurDaoImpl.voirDes(j);
		return listeRenv;
	}

	@Override
	public void terminerTour() throws NoCurrentGameException {
		partieCourante = joueurDaoImpl.terminerTour();
		partieDaoImpl.mettreAJour(partieCourante);
	}

	@Override
	public Joueur seConnecter(String pseudo, String mdp) throws ValidationException {
		if (!Utils.checkString(pseudo) || !Pattern.matches("[a-zA-Z0-9]{1,20}", pseudo)) {
			throw new ValidationException("Format du pseudo incorrecte.");
		}
		return joueurDaoImpl.connecter(pseudo, mdp);
	}

	@Override
	public Partie creerPartie(String nom) throws ValidationException, XmlParsingException {
		if (!Utils.checkString(nom) || !Pattern.matches("[a-zA-Z0-9]{1,20}", nom))
			throw new ValidationException("Format de la partie invalide.");
		//Si aucune partie n'est cree sur le serveur, on charge le xml une seule fois
		if(partieCourante==null&&partieDaoImpl.getPartieCourante()==null)
			xmlParserImpl.chargerXML();
		partieCourante = partieDaoImpl.creerUnePartie(nom);
		return partieCourante;
	}

	@Override
	public void deconnecter(Joueur j) {
		partieCourante = joueurDaoImpl.deconnecter(j, min_joueurs);
	}

	public Joueur getJoueurCourant() {
		return partieCourante.getCourant().getJoueur();
	}

	public int getMin_joueurs() {
		return min_joueurs;
	}

	public void setMin_joueurs(int min_joueurs) {
		this.min_joueurs = min_joueurs;
	}

	public int getMax_joueurs() {
		return max_joueurs;
	}

	public void setMax_joueurs(int max_joueurs) {
		this.max_joueurs = max_joueurs;
	}

	public int getNbCartesParJoueurs() {
		return nbCartesParJoueurs;
	}

	public void setNbCartesParJoueurs(int nbCartesParJoueurs) {
		this.nbCartesParJoueurs = nbCartesParJoueurs;
	}

	public int getNbCartesTotal() {
		return nbCartesTotal;
	}

	public void setNbCartesTotal(int nbCartesTotal) {
		this.nbCartesTotal = nbCartesTotal;
	}

	public int getNbDesParJoueur() {
		return nbDesParJoueur;
	}

	public void setNbDesParJoueur(int nbDesParJoueur) {
		this.nbDesParJoueur = nbDesParJoueur;
	}

	public int getNbDesTotal() {
		return nbDesTotal;
	}

	public void setNbDesTotal(int nbDesTotal) {
		this.nbDesTotal = nbDesTotal;
	}

	@Override
	public Partie getPartieCourante() throws NoCurrentGameException {
		if (partieCourante == null)
			throw new NoCurrentGameException();
		return partieCourante;
	}

	@Override
	public void donnerDes(Joueur j, int[] id_adversaires) throws NotEnoughDiceException {
		for(int i = 0; i<id_adversaires.length;i++){
			Joueur adverse = joueurDaoImpl.rechercher(id_adversaires[i]);
			deDaoImpl.donnerDe(adverse);
		}
	}

	@Override
	public void utiliserCarte(int id_carte) throws CardNotFoundException {
		Carte c = carteDaoImpl.rechercher(id_carte);
		try {
			gc.utiliserCarte(c);
		} catch (CardConstraintViolatedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void utiliserCarte(int id_carte, Joueur j) throws CardNotFoundException {
		Carte c = carteDaoImpl.rechercher(id_carte);
		try {
			gc.utiliserCarte(c, j);
		} catch (CardConstraintViolatedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void utiliserCarte(int id_carte, Sens sens) throws CardNotFoundException {
		Carte c = carteDaoImpl.rechercher(id_carte);
		try {
			gc.utiliserCarte(c, sens);
		} catch (CardConstraintViolatedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Carte> voirCartes(Joueur j) {
		return joueurDaoImpl.voirCartes(j);
	}

	@Override
	public int getNombreDeToursAPasser(Joueur j) {
		return joueurPartieDaoImpl.getJoueurDeLaPartieCourante(j).getCompteur_sauts();
	}

	@Override
	public Carte piocherUneCarte(Joueur j) {
		return joueurDaoImpl.piocherCarte(j);
	}

	@Override
	public Carte remettreCarte(Joueur joueur, Carte carte) {
		return joueurDaoImpl.remettreCarte(joueur, carte);
	}

	@Override
	public Carte piocherUneCarteChezUnJoueur(Carte c) {
		return joueurDaoImpl.piocherCarteChezUnJoueur(c);

	}

	@Override
	public boolean laisserAdversaireAvecDeuxCartes(Carte c,Joueur j) {
		return joueurDaoImpl.laisserAdversaireAvecUneCartes(c,j);
	}

	@Override
	public boolean laisserTousAdversairesAvecDeuxCartes(Carte c) {
		return joueurDaoImpl.laisserToutLesAdversairesAvecDeuxCartes();
	}


	@Override
	public boolean passerTour(Carte c, Joueur j) {
		// TODO Auto-generated method stub
		return joueurDaoImpl.passerTour(c, j);
	}


	@Override
	public void supprimerDe(Joueur joueur) {
		try {
			JoueurPartie joueurPartie = joueurPartieDaoImpl.getJoueurDeLaPartieCourante(joueur);
			joueurPartieDaoImpl.supprimerDe(joueurPartie);
		} catch (NotEnoughDiceException e) {
			System.out
					.println("Player " + joueur.getPseudo() + " tried to remove one of his dice but already had none.");
		}
	}

	@Override
	public Joueur getJoueurSuivant(Joueur actuel, Sens sens) {
		JoueurPartie joueurPartie = joueurPartieDaoImpl.getJoueurDeLaPartieCourante(actuel);
		JoueurPartie suivant = null;
		try {
			switch (sens) {
			case ANTIHORAIRE:
				suivant = joueurPartieDaoImpl.getJoueurPrecedent(joueurPartie, getPartieCourante());
				break;
			case HORAIRE:
				suivant = joueurPartieDaoImpl.getJoueurSuivant(joueurPartie, getPartieCourante());
				break;
			default:
				return null;
			}
		} catch (NoCurrentGameException e) {
			return null;
		}
		return suivant.getJoueur();
	}

	public void changementDeSens(Sens sens) throws NoCurrentGameException {
		partieCourante.setSens(sens);
		partieCourante = partieDaoImpl.mettreAJour(partieCourante);
	}

	public void setPioche(List<Carte> pioche) {
		this.pioche = pioche;
	}

	@Override
	public int getNbWazabi(Joueur joueur) {
		int count = 0;
		List<De> des = joueurDaoImpl.voirDes(joueur);
		for (De de : des) {
			if (de.getValeur() == Valeur.WAZABI) {
				count++;
			}
		}
		return count;
	}

	@Override
	public void donnerDes(Joueur j, int id_joueur) {
		// TODO Auto-generated method stub
		Joueur adverse = joueurDaoImpl.rechercher(id_joueur);
		deDaoImpl.donnerDe(adverse);
		
	}

}
