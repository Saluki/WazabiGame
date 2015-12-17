package ovh.gorillahack.wazabi.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;

@SuppressWarnings("serial")
@Stateless
@Local(Dao.class)
@LocalBean
public class CarteDaoImpl extends DaoImpl<Carte> {
	@EJB
	PartieDaoImpl partieDaoImpl;
	@PersistenceContext(unitName = "wazabi")
	private EntityManager entityManager;

	public CarteDaoImpl() {
		super(Carte.class);
	}

	/**
	 * 
	 * Permet de récupérer la liste des cartes d'un joueur.
	 * 
	 * @param Joueur
	 *            j: le joueur dont on veut obtenir les cartes.
	 * @return La liste des cartes du joueur.
	 */
	public List<Carte> getCartes(Joueur j) {
		return super.liste("SELECT c FROM Carte c, JoueurPartie jp WHERE c MEMBER OF jp.cartes "
				+ "AND jp.joueur=?1 AND jp.partie=?2", j, partieDaoImpl.getPartieCourante());
	}
}
