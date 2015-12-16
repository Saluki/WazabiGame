package ovh.gorillahack.wazabi.usecases;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import ovh.gorillahack.wazabi.dao.JoueurDaoImpl;
import ovh.gorillahack.wazabi.dao.PartieDaoImpl;
import ovh.gorillahack.wazabi.dao.XmlParserImpl;
import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardNotFoundException;
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;
import ovh.gorillahack.wazabi.exception.NotEnoughDiceException;
import ovh.gorillahack.wazabi.exception.PlayerNotFoundException;
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
	private List<Carte> jeuDeCarte;

	@EJB
	private JoueurDaoImpl joueurDaoImpl;

	@EJB
	private PartieDaoImpl partieDaoImpl;

	@EJB
	private XmlParserImpl xmlParserImpl;

	@PostConstruct
	public void postconstruct() {
		System.out.println("GestionPartieImpl created");
	}

	@PreDestroy
	public void predestroy() {
		System.out.println("GestionPartieImpl destroyed");
	}

	/**
	 * Default constructor.
	 */
	public GestionPartieImpl() {
		// TODO Lors de la selection du joueur courant, il faut prendre en compte le champ "compteur_saut".
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
	public List<Partie> afficherHistorique(Joueur j) throws PlayerNotFoundException{
		if(j == null)
			throw new PlayerNotFoundException("Le joueur n'existe pas");
		List<Partie> listeRenv = partieDaoImpl.afficherHistorique(j);
		return listeRenv ;
	}

	@Override
	public void rejoindrePartie(Joueur j) throws NoCurrentGameException {
		partieDaoImpl.rejoindrePartie(j);
		int nbJoueursSalon = listerJoueurPartieCourante().size();
		if(nbJoueursSalon>=min_joueurs)
			commencerPartie();
	}

	@Override
	public  List<Joueur> listerJoueurPartieCourante() throws NoCurrentGameException{
		List<Joueur> listeRenv  = partieDaoImpl.listerJoueurPartieCourante();
		if(  listeRenv.isEmpty())
			throw new NoCurrentGameException("Aucune partie n'est en cours");
		return listeRenv;
	}

	@Override
	public List<Joueur> getAdversaires(Joueur j) throws PlayerNotFoundException, NoCurrentGameException{
		if(j == null)
			throw new PlayerNotFoundException("Le joueur n'existe pas");
		List<Joueur> adversaires = listerJoueurPartieCourante();
		if(adversaires.isEmpty())
			throw new NoCurrentGameException("Aucune partie n'est en cours");
		adversaires.remove(j);
		return adversaires;
	}

	@Override
	public void commencerPartie() throws NoCurrentGameException{
		partieCourante = partieDaoImpl.commencerPartie(nbCartesParJoueurs, nbDesParJoueur);
		if(partieCourante == null)
			throw new NoCurrentGameException("La partie n'a pas pu être lancé . Veuiller reesayer");
	}

	@Override
	public List<De> lancerDes(Joueur j) throws PlayerNotFoundException{
		if(j == null)
			throw new PlayerNotFoundException("Le joueur n'existe pas");
		List<De> listeRenv = joueurDaoImpl.lancerDes(j);
		if(listeRenv.isEmpty())
			throw new PlayerNotFoundException("Le joueur n'existe pas");
		return listeRenv ;
	}

	@Override
	public List<De> voirDes(Joueur j) throws PlayerNotFoundException{
		if(j == null)
			throw new PlayerNotFoundException("Le joueur n'existe pas");
		List<De> listeRenv = joueurDaoImpl.voirDes(j);
		if(listeRenv.isEmpty())
			throw new PlayerNotFoundException("Le joueur n'existe pas");
		return listeRenv ;
	}

	@Override
	public boolean piocherUneCarte(Joueur j) throws PlayerNotFoundException{
		if(j == null)
			throw new PlayerNotFoundException("Le joueur n'existe pas");
		return joueurDaoImpl.piocherCarte(j);
		
	}

	@Override
	public void terminerTour() throws NoCurrentGameException{
		partieCourante = joueurDaoImpl.terminerTour();
	}

	@Override
	public Joueur seConnecter(String pseudo, String mdp) throws ValidationException {
		if (!Utils.checkString(pseudo) || !Pattern.matches("[a-zA-Z0-9]{1,20}", pseudo)) {
			throw new ValidationException("Format du pseudo incorrecte.");
		}

		if (!Utils.checkString(mdp) || !Pattern.matches("[a-zA-Z0-9]{1,20}", mdp)) {
			throw new ValidationException("Format du mot de passe incorrecte.");
		}
		return joueurDaoImpl.connecter(pseudo, mdp);
	}

	@Override
	public Partie creerPartie(String nom) throws ValidationException, XmlParsingException {
		if (!Utils.checkString(nom) || !Pattern.matches("[a-zA-Z0-9]{1,20}", nom))
			throw new ValidationException("Format de la partie invalide.");
		xmlParserImpl.chargerXML();
		
		partieCourante = partieDaoImpl.creerUnePartie(nom);

		return partieCourante;
	}

	@Override
	public void deconnecter(Joueur j) throws PlayerNotFoundException{
		if(j == null)
			throw new PlayerNotFoundException("Le joueur n'existe pas");
		joueurDaoImpl.deconnecter(j, min_joueurs);
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
	public Partie getPartieCourante() throws NoCurrentGameException{
		if(partieCourante==null)
			throw new NoCurrentGameException();
		return partieCourante;
	}

	@Override
	public List<Carte> getJeuDeCarte() {
		return jeuDeCarte;
	}

	@Override
	public void setJeuDeCarte(List<Carte> liste) {
		this.jeuDeCarte = liste;

	}
	
	@Override
	public void donnerDes(Joueur j, int[] id_adversaires) throws NotEnoughDiceException,PlayerNotFoundException {
		if(j == null)
			throw new PlayerNotFoundException("Le joueur n'existe pas");
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void utiliserCarte(int id_carte) throws CardNotFoundException{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void utiliserCarte(int id_carte, Joueur j) throws CardNotFoundException, PlayerNotFoundException{
		if(j == null)
			throw new PlayerNotFoundException("Le joueur n'existe pas");
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void utiliserCarte(int id_carte, Sens sens) throws CardNotFoundException{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Carte> voirCartes(Joueur j) throws PlayerNotFoundException{
		if(j == null)
			throw new PlayerNotFoundException("Le joueur n'existe pas");
		return joueurDaoImpl.voirCartes(j);
	}
	
	@Override
	public int getNombreDeToursAPasser(Joueur j) throws PlayerNotFoundException{
		if(j == null)
			throw new PlayerNotFoundException("Le joueur n'existe pas");
		return 0;
	}
}
