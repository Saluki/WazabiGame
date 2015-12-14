package ovh.gorillahack.wazabi.usecases;

import java.util.List;

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

/**
 * Session Bean implementation class GestionPartieImpl
 */
@Singleton
@Startup
@Remote(GestionPartie.class)
public class GestionPartieImpl implements GestionPartie {
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
    	Joueur joueur = new Joueur(pseudo, motdepasse);
    	joueurDaoImpl.enregistrer(joueur);
    	return joueur;
    }

	@Override
	public List<Partie> afficherHistorique(Joueur j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partie rejoindrePartie(Joueur j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Joueur> listerJoueurPartieCourante() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Joueur commencerPartie() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<De> lancerDe(Joueur j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<De> voirDe(Joueur j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Carte piocherUneCarte(Joueur j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void terminerTour() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Joueur seConnecter(String pseudo, String mdp) {
		// TODO Auto-generated method stub
		return null;
	}

}
