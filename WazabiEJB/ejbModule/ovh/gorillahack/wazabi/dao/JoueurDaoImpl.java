package ovh.gorillahack.wazabi.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ovh.gorillahack.wazabi.domaine.Joueur;

/**
 * Session Bean implementation class JoueurDaoImpl
 */
@Stateless
@Local(Dao.class)
@LocalBean
public class JoueurDaoImpl implements Dao<Joueur> {
	@PersistenceContext(unitName = "wazabi")
	private EntityManager entityManager;
	
    public JoueurDaoImpl() {
    }

	/**
     * @see Dao#enregistrer(E)
     */
    public Joueur enregistrer(Joueur entite) {
    	entityManager.persist(entite);
		return entite;
    }

	/**
     * @see Dao#lister()
     */
    public List<Joueur> lister() {
    	List<Joueur> joueurs = null;
		String queryString = "select j from Joueur j";
		TypedQuery<Joueur> query = entityManager.createQuery(queryString, Joueur.class);
		joueurs = query.getResultList();
		return joueurs;
    }
}
