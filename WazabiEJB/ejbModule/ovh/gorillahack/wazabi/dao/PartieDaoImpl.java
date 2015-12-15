package ovh.gorillahack.wazabi.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.domaine.Partie.Status;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

@SuppressWarnings("serial")
@Stateful
@Local(Dao.class)
@LocalBean
public class PartieDaoImpl extends DaoImpl<Partie> {
	protected static int ordre = 0;
	@EJB
	private JoueurPartieDaoImpl joueurPartieDao;
	@EJB
	private CarteDaoImpl carteDaoImpl;
	@EJB
	private DeDaoImpl deDaoImpl;
	@EJB
	private FaceDaoImpl faceDaoImpl;
	@EJB
	private GestionPartie gestionPartie;
	
	@EJB
	private JoueurDaoImpl joueurDaoImpl;

	public PartieDaoImpl() {
		super(Partie.class);
	}

	@PersistenceContext(unitName = "wazabi")
	private EntityManager entityManager;
	public Partie creerUnePartie(String nom) {
		Partie partie = super.enregistrer(new Partie(nom, new Date(), Sens.HORAIRE, null, null, null, Status.EN_ATTENTE));
		List<Carte> cartes = gestionPartie.getJeuDeCarte();
		for (int i = 0; i < cartes.size(); i++) {
			Carte carte = cartes.get(i);
			carteDaoImpl.enregistrer(carte);
			partie.ajouterCarteALaPioche(carte);
		}
		return partie;
	}

	public Partie rejoindrePartie(Joueur j) {
		JoueurPartie jp = new JoueurPartie(ordre++, 0, deDaoImpl.getDes(j), carteDaoImpl.getCartes(j));
		Partie p = getPartieCourante();
		if (p == null || p.getStatut() == Partie.Status.PAS_COMMENCE) {
			return null;
		}
		jp.setPartie(p);
		jp.setJoueur(j);
		if(p.getCourant()==null){
			p.setCourant(jp);
		}
		joueurPartieDao.enregistrer(jp);
		return super.enregistrer(p);
	}

	public List<Partie> afficherHistorique(Joueur j) {
		return super.liste("SELECT p FROM Partie p WHERE EXISTS("
				+ "SELECT jp FROM JoueurPartie jp WHERE jp.joueur = ?1 AND jp.partie = p.id_partie)", j);
	}

	public Partie getPartieCourante() {
		return super.recherche("SELECT p FROM Partie p WHERE p.id_partie = (SELECT MAX(p.id_partie) FROM Partie p)");
	}
	
	public List<Joueur> listerJoueurPartieCourante() {
		return joueurDaoImpl.listerJoueurPartieCourante();
	}
	
	public Partie commencerPartie(int nbCartesParJoueur) {
		Partie p = getPartieCourante();
		if(p==null||p.getStatut()!=Status.EN_ATTENTE)
			return null;
		p.setStatut(Status.COMMENCE);
		for(Joueur j: listerJoueurPartieCourante()){
			//TODO Mettre 4 dés pour chaque joueur
			for(int i = 0; i<nbCartesParJoueur;i++){
				joueurDaoImpl.piocherCarte(j);
			}
		}
				
		p.setCourant(joueurPartieDao.getJoueurCourant());
		super.mettreAJour(p);
		return p;
	}

}
