package ovh.gorillahack.wazabi.usecases;

import java.util.List;
import java.util.Map;
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
import ovh.gorillahack.wazabi.dao.CarteEffetDaoImpl;
import ovh.gorillahack.wazabi.dao.DeDaoImpl;
import ovh.gorillahack.wazabi.dao.FaceDaoImpl;
import ovh.gorillahack.wazabi.dao.JoueurDaoImpl;
import ovh.gorillahack.wazabi.dao.JoueurPartieDaoImpl;
import ovh.gorillahack.wazabi.dao.PartieDaoImpl;
import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.CarteEffet;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Face;
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
import ovh.gorillahack.wazabi.util.XmlParserImpl;

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

	private XmlParserImpl xmlParserImpl;

	@EJB
	private CarteDaoImpl carteDaoImpl;

	@EJB
	private DeDaoImpl deDaoImpl;

	@EJB
	private FaceDaoImpl faceDaoImpl;

	@EJB
	private CarteEffetDaoImpl carteEffetDaoImpl;

	@PostConstruct
	public void postconstruct() {
		System.out.println("GestionPartieImpl created");
		try {
			//Ajout des joueurs par d�faut
			if(joueurDaoImpl.getJoueur("em")==null)
				inscrire("em", "em", "em");
			if(joueurDaoImpl.getJoueur("mi")==null)
				inscrire("mi", "mi", "mi");
			if(joueurDaoImpl.getJoueur("ol")==null)
				inscrire("ol", "ol", "ol");
			//Creation de la chaine de responsabilit� du traitement de cartes
			this.gc = new GestionnaireCarteEnleverDe(new GestionnaireCarteTournerDe(new GestionnaireCarteSupprimerDe(
					new GestionnaireCarteDonnerDe(new GestionnaireCartePrendreCarte(new GestionnaireCarteLaisserCarte(
							new GestionnaireCartePioche3(new GestionnaireCarteLaisser2CartesAdversaires(
									new GestionnaireCartePasserTour(new GestionnaireCarteChangerSens(null))))))))));
			//Initialisation du XMLParser
			xmlParserImpl = new XmlParserImpl();
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
		return partieDaoImpl.afficherHistorique(j);
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
		 * TODO faudra reinitialiser toutes les donn�es si on veut pouvoir
		 * reutiliser le m�me paquet de cartes (ex id_joueur, id_partie, ...)
		 */
		partieDaoImpl.enregistrerPioche(pioche);
		partieCourante = partieDaoImpl.commencerPartie(nbCartesParJoueurs, nbDesParJoueur);
		if (partieCourante == null)
			throw new NoCurrentGameException("La partie n'a pas pu �tre lanc� . Veuiller reesayer");
	}

	@Override
	public List<De> lancerDes(Joueur j) {
		List<De> listeRenv = joueurDaoImpl.lancerDes(j);
		for (int i = 0; i < listeRenv.size(); i++) {
			De d = listeRenv.get(i);
			if (d.getValeur() == Valeur.PIOCHE) {
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
			throw new ValidationException("Format du pseudo incorrect");
		}
		return joueurDaoImpl.connecter(pseudo, mdp);
	}

	@Override
	public Partie creerPartie(String nom) throws ValidationException, XmlParsingException {
		if (!Utils.checkString(nom) || !Pattern.matches("[a-zA-Z0-9]{1,20}", nom))
			throw new ValidationException("Format de la partie invalide.");
		// Si aucune partie n'est cree sur le serveur, on charge le xml une
		// seule fois
		if (partieCourante == null && partieDaoImpl.getPartieCourante() == null) {
			setParameters();
			setCards();
			setDices();

		} else{
			pioche = carteDaoImpl.lister();
		}
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

	@Override
	public Partie getPartieCourante() throws NoCurrentGameException {
		if (partieCourante == null)
			throw new NoCurrentGameException();
		return partieCourante;
	}

	@Override
	public void donnerDes(Joueur j, int[] id_adversaires) throws NotEnoughDiceException {
		for (int i = 0; i < id_adversaires.length; i++) {
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
			// if (c.getCout() > getNbWazabi(getJoueurCourant())) {
			// throw new CardConstraintViolatedException(
			// "Le joueur " + getJoueurCourant() + " n'a pas assez de wazabi
			// pour jouer cette carte.");
			// }
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
	public boolean laisserAdversaireAvecUneCarte(Carte c, Joueur j) {
		return joueurDaoImpl.laisserAdversaireAvecUneCartes(c, j);
	}

	@Override
	public boolean laisserTousAdversairesAvecDeuxCartes(Carte c) {
		return joueurDaoImpl.laisserToutLesAdversairesAvecDeuxCartes();
	}

	@Override
	public boolean passerTour(Carte c, Joueur j) {
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

	private void setParameters() throws XmlParsingException {
		Map<String, Integer> params = xmlParserImpl.parseParametres();
		this.min_joueurs = params.get("MIN_JOUEURS");
		this.max_joueurs = params.get("MAX_JOUEURS");
		this.nbCartesParJoueurs = params.get("NB_CARTES_PAR_JOUEUR");
		this.nbCartesTotal = params.get("NB_CARTES_TOTAL");
		this.nbDesParJoueur = params.get("NB_DES_PAR_JOUEUR");
		this.nbDesTotal = params.get("NB_DES_TOTAL");
	}

	private void setCards() throws XmlParsingException {
		Map<CarteEffet, Integer> map = xmlParserImpl.parseCartesEffet();

		for (CarteEffet ce : map.keySet()) {
			carteEffetDaoImpl.enregistrer(ce);
		}
		List<Carte> cartes = xmlParserImpl.parseCarte(map);
		for (Carte c : cartes)
			carteDaoImpl.enregistrer(c);
		pioche = carteDaoImpl.lister();
	}

	private void setDices() throws XmlParsingException {
		List<Face> faces = xmlParserImpl.parseDes();
		faceDaoImpl.enregistrer(faces);
		for (int i = 0; i < nbDesTotal; i++) {
			deDaoImpl.enregistrer(new De());
		}
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
		Joueur adverse = joueurDaoImpl.rechercher(id_joueur);
		deDaoImpl.donnerDe(adverse);
	}

	@Override
	public void changementDeSens() throws NoCurrentGameException {
		if (getPartieCourante().getSens() == Sens.HORAIRE) {
			changementDeSens(Sens.ANTIHORAIRE);
		} else if (getPartieCourante().getSens() == Sens.ANTIHORAIRE) {
			changementDeSens(Sens.HORAIRE);
		}
		List<Joueur> adversaires = getAdversaires(getJoueurCourant());

		// on ajoute un saut � tous les adversaires comme impl�mentation de
		// rejouer
		for (Joueur joueur : adversaires) {
			JoueurPartie adversaire = joueurPartieDaoImpl.getJoueurDeLaPartieCourante(joueur);
			adversaire = joueurPartieDaoImpl.recharger(adversaire.getId_joueur_partie());
			adversaire.ajouterSaut();
		}
	}

	@Override
	public Joueur getPlayerFromId(int id_player) {
		return joueurDaoImpl.rechercher(id_player);
	}

	@Override
	public boolean faireTournerLesDes(Carte c, Sens sens) {
		return deDaoImpl.faireTournerDes(c,sens);
	}

}
