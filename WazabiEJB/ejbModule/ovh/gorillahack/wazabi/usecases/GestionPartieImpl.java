package ovh.gorillahack.wazabi.usecases;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import ovh.gorillahack.wazabi.dao.JoueurDaoImpl;
import ovh.gorillahack.wazabi.dao.PartieDaoImpl;
import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Status;

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
	
	@EJB
	private JoueurDaoImpl joueurDaoImpl;
	
	@EJB
	private PartieDaoImpl partieDaoImpl;
		
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
    public Joueur inscrire(String pseudo, String motdepasse) {
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
		return joueurDaoImpl.listerJoueurPartieCourante();
	}
	
	@Override
	public List<Joueur> getAdversaires(Joueur j) {
		List<Joueur> adversaires = listerJoueurPartieCourante();
		adversaires.remove(j);
		return adversaires;
	}

	@Override
	public void commencerPartie() {
		joueurDaoImpl.commencerPartie();
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
	public Joueur seConnecter(String pseudo, String mdp) {
		return joueurDaoImpl.connecter(pseudo, mdp);
	}
	
	@Override
	public Partie creerPartie(String nom) {
		partieCourante = partieDaoImpl.creerUnePartie(nom);
		return partieCourante;
	}
	
	private void recupererDonnees(){
		partieCourante = partieDaoImpl.getPartieCourante();
		List<Joueur> joueursDeLaPartie = listerJoueurPartieCourante();
		if(partieCourante.getStatut() == Status.EN_ATTENTE){
			
		} else if(partieCourante.getStatut() == Status.COMMENCE){
			
		}
	}
	
	public Joueur getJoueurCourant(){
		return partieCourante.getCourant().getJoueur();
	}

}
