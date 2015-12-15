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
import ovh.gorillahack.wazabi.domaine.Partie.Status;
import ovh.gorillahack.wazabi.exception.ValidationException;
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
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public Joueur inscrire(String pseudo, String motdepasse,String motdepasseRepeat) throws ValidationException {
    	if (!Utils.checkString(pseudo) || ! Pattern.matches("([a-z]|[0-9]){1,20}", pseudo)) {
    		throw new ValidationException("Format du pseudo invalide .");
		}
				
		if (!Utils.checkString(motdepasse) || ! Pattern.matches("([a-z]|[0-9]){1,20}", motdepasse)) {
			throw new ValidationException("Format du mot de passe invalide.");
		}
		
		if (!motdepasse.equals(motdepasseRepeat) || ! Pattern.matches("([a-z]|[0-9]){1,20}", motdepasseRepeat)) {
			throw new ValidationException("Les deux mots de passe ne sont pas similaires.");
		}
		
    	return joueurDaoImpl.inscrire(pseudo, motdepasse);
    }
        
	@Override
	public List<Partie> afficherHistorique(Joueur j) {
		return partieDaoImpl.afficherHistorique(j);
	}

	@Override
	public Partie rejoindrePartie(Joueur j) {
		partieCourante  = partieDaoImpl.rejoindrePartie(j);
		return partieCourante;
	}

	@Override
	public List<Joueur> listerJoueurPartieCourante() {
		return partieDaoImpl.listerJoueurPartieCourante();
	}
	
	@Override
	public List<Joueur> getAdversaires(Joueur j) {
		List<Joueur> adversaires = listerJoueurPartieCourante();
		adversaires.remove(j);
		return adversaires;
	}

	@Override
	public void commencerPartie() {
		partieCourante = partieDaoImpl.commencerPartie(nbCartesParJoueurs);
	}

	@Override
	public List<De> lancerDes(Joueur j) {
		return joueurDaoImpl.lancerDes(j);
	}

	@Override
	public List<De> voirDes(Joueur j) {
		return joueurDaoImpl.voirDes(j);
	}

	@Override
	public Carte piocherUneCarte(Joueur j) {
		return joueurDaoImpl.piocherCarte(j);
	}

	@Override
	public void terminerTour() {
		joueurDaoImpl.terminerTour();
	}

	@Override
	public Joueur seConnecter(String pseudo, String mdp)throws ValidationException {
		if (!Utils.checkString(pseudo)|| ! Pattern.matches("([a-z]|[0-9]){1,20}", pseudo)) {
			throw new ValidationException("Format du pseudo incorrecte.");
		}

		if (!Utils.checkString(mdp)|| ! Pattern.matches("([a-z]|[0-9]){1,20}", mdp)) {
			throw new ValidationException("Format du mot de passe incorrecte.");
		}
		return joueurDaoImpl.connecter(pseudo, mdp);
	}
	
	@Override
	public Partie creerPartie(String nom) throws ValidationException {
		if(! Utils.checkString(nom) || ! Pattern.matches("[A-Za-z0-9]{1,20}", nom))
			throw new ValidationException("Format de la partie invalide.");
		xmlParserImpl.chargerXML();
		partieCourante = partieDaoImpl.creerUnePartie(nom);
		return partieCourante;
	}
	

	@Override
	public void deconnecter(Joueur j){
		joueurDaoImpl.deconnecter(j,min_joueurs);
	}
		
	public Joueur getJoueurCourant(){
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
	public Partie getPartieCourante(){
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
}
