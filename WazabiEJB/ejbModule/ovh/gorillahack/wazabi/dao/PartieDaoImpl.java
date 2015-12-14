package ovh.gorillahack.wazabi.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;


@Stateless
@Local(Dao.class)
@LocalBean
public class PartieDaoImpl implements Dao<Partie>{

	@PersistenceContext(unitName = "wazabi")
	private EntityManager entityManager;

	@Override
	public Partie enregistrer(Partie entite) {
		entityManager.persist(entite);
		return entite;
	}

	@Override
	public List<Partie> lister() {
		List<Partie> parties = null;
		String queryString = "select p from Partie p";
		TypedQuery<Partie> query = entityManager.createQuery(queryString, Partie.class);
		parties = query.getResultList();
		return parties;
	}
	
	

}
