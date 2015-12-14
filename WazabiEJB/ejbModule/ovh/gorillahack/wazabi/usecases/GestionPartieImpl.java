package ovh.gorillahack.wazabi.usecases;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import ovh.gorillahack.wazabi.dao.DeDaoImpl;
import ovh.gorillahack.wazabi.dao.JoueurDaoImpl;
import ovh.gorillahack.wazabi.dao.JoueurPartieDaoImpl;
import ovh.gorillahack.wazabi.dao.PartieDaoImpl;
import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.domaine.Partie.Status;

/**
 * Session Bean implementation class GestionPartieImpl
 */
@Singleton
@Startup
@Remote(GestionPartie.class)
public class GestionPartieImpl implements GestionPartie {
	private Partie partieCourante;
	
	@EJB
	private JoueurDaoImpl joueurDaoImpl;
	
	@EJB
	private PartieDaoImpl partieDaoImpl;
	
	@EJB
	private JoueurPartieDaoImpl joueurPartieDao;
	
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
		return partieDaoImpl.rejoindrePartie(j);
	}

	@Override
	public List<Joueur> listerJoueurPartieCourante() {
		return joueurPartieDao.listerJoueurPartieCourante();
	}

	@Override
	public Joueur commencerPartie() {
		return joueurPartieDao.commencerPartie();
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
		joueurPartieDao.terminerTour();
	}

	@Override
	public Joueur seConnecter(String pseudo, String mdp) {
		return joueurDaoImpl.connecter(pseudo, mdp);
	}

}
