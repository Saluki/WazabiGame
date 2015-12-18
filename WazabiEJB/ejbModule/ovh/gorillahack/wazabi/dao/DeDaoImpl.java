package ovh.gorillahack.wazabi.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Face;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;
import ovh.gorillahack.wazabi.exception.NotEnoughDiceException;

@Stateless
@Local(Dao.class)
@LocalBean
public class DeDaoImpl extends DaoImpl<De> {
	private static final long serialVersionUID = -1293913566498317290L;
	@PersistenceContext(unitName = "wazabi")
	private EntityManager entityManager;

	@EJB
	private PartieDaoImpl partieDaoImpl;
	@EJB
	private FaceDaoImpl faceDaoImpl;
	@EJB
	private JoueurPartieDaoImpl joueurPartieDaoImpl;

	public DeDaoImpl() {
		super(De.class);
	}

	public List<De> getDes(Joueur j) {
		return super.liste("SELECT d FROM De d, JoueurPartie jp WHERE d MEMBER OF jp.des "
				+ "AND jp.joueur=?1 AND jp.partie=?2", j, partieDaoImpl.getPartieCourante());
	}

	public List<De> creerDes(int nombre) {
		ArrayList<De> des = new ArrayList<>();
		for (int i = 0; i < nombre; i++) {
			De de = new De();
			super.enregistrer(de);
		}
		return des;
	}

	public De lancerDe(De de) {
		List<Face> list = faceDaoImpl.getAllFaces();
		// on crée une liste contenant toutes les valeurs le nombre de fois
		// qu'elles sont présentes sur le dé.
		List<Face.Valeur> valeursList = new ArrayList<>();
		for (Face face : list) {
			for (int i = 0; i < face.getNbFaces(); i++) {
				valeursList.add(face.getValeur_face());
			}
		}		
		Random random = new Random();
		int index = random.nextInt(valeursList.size());
		de.setValeur(valeursList.get(index));
		mettreAJour(de);
		return de;
	}
	
	public void donnerDe(Joueur j){
		JoueurPartie courant = partieDaoImpl.getPartieCourante().getCourant();
		JoueurPartie jp = joueurPartieDaoImpl.getJoueurDeLaPartieCourante(j);
		De de = courant.getDes().remove(0);
		List<De> des = jp.getDes();
		des.add(de);
		jp.setDes(des);
		
		joueurPartieDaoImpl.mettreAJour(jp);
		joueurPartieDaoImpl.mettreAJour(courant);
		
	}
}
