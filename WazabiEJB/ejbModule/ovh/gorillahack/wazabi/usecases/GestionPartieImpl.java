package ovh.gorillahack.wazabi.usecases;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;

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
	private Partie partieCourrante;
	
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
    	Joueur joueur = new Joueur(pseudo, motdepasse);
    	joueurDaoImpl.enregistrer(joueur);
    	return joueur;
    }
        
    public Partie enregistrerPartie(String nom, Date timestamp, Sens sens, Joueur vainqueur, List<Carte> cartes,
			JoueurPartie courant, Status statut){
    	Partie p= new Partie(nom,timestamp,sens,vainqueur,cartes,courant, statut);
    	p.setCartes(cartes);
		p.setCourant(courant);
    	p = partieDaoImpl.enregistrer(p);
    	return p;
    }
        
    public JoueurPartie enregistrerJoueurPartie(Joueur j, Partie partie){
    	JoueurPartie p = new JoueurPartie(0,0,null,null);
    	p.setJoueur(j);
    	p.setPartie(partie);
    	joueurPartieDao.enregistrer(p);
    	return p;
    }

	@Override
	public List<Partie> afficherHistorique(Joueur j) {
		return partieDaoImpl.afficherHistorique(j);
	}

	@Override
	public Partie rejoindrePartie(Joueur j) {
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
