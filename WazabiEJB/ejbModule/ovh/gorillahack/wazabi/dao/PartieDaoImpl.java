package ovh.gorillahack.wazabi.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.domaine.Partie.Status;
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;
import ovh.gorillahack.wazabi.exception.QueryException;
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
		Partie partie = super.enregistrer(
				new Partie(nom, new Date(), Sens.HORAIRE, null, null, null, Status.EN_ATTENTE));

		/*List<Carte> cartes = gestionPartie.getJeuDeCarte();
		for (int i = 0; i < cartes.size(); i++) {
			Carte carte = cartes.get(i);
			carteDaoImpl.enregistrer(carte);
			partie.ajouterCarteALaPioche(carte);
		}*/
		return partie;
	}

	public Partie rejoindrePartie(Joueur j) throws NoCurrentGameException {
		if (joueurPartieDao.getJoueurDeLaPartieCourante(j) == null) {
			JoueurPartie jp = new JoueurPartie(ordre++, 0, null, null);
			Partie p = getPartieCourante();
			if (p == null || p.getStatut() == Partie.Status.PAS_COMMENCE || p.getStatut() == Status.ANNULEE)
				throw new NoCurrentGameException();

			jp.setPartie(p);
			jp.setJoueur(j);
			if (p.getCourant() == null) {
				p.setCourant(jp);
			}
			joueurPartieDao.enregistrer(jp);
			return super.enregistrer(p);
		}
		return getPartieCourante();
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

	public Partie commencerPartie(int nbCartesParJoueur, int nbDesParJoueurs) {
		Partie p = getPartieCourante();
		if (p == null || p.getStatut() != Status.EN_ATTENTE)
			return null;
		p.setStatut(Status.COMMENCE);
		int cpt = 1;

		/*
		 * for (Joueur j : listerJoueurPartieCourante()) { List<De> des = new
		 * ArrayList<De>(); int nbDes = 0; for (nbDes = 0; nbDes <
		 * nbDesParJoueurs; nbDes++) { des.add(deDaoImpl.rechercher(cpt++)); }
		 * joueurPartieDao.setDes(j, des); for (int i = 0; i <
		 * nbCartesParJoueur; i++) { joueurDaoImpl.piocherCarte(j); } }
		 */

		p.setCourant(joueurPartieDao.getJoueurCourant());
		super.mettreAJour(p);
		return p;
	}

//	public int getMaxOrdrePioche(Partie partie) throws QueryException {
//		return super.rechercheInt("SELECT MAX (c.ordre_pioche) FROM Partie p, Carte c "
//				+ "WHERE p.id_partie = ?1 AND c MEMBER OF p.pioche", partie);
//	}

}
