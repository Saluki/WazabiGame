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
public class JoueurDaoImpl extends DaoImpl<Joueur> {
	@PersistenceContext(unitName = "wazabi")
	private EntityManager entityManager;
	
    public JoueurDaoImpl() {
    	super(Joueur.class);
    }

    public List<Joueur> afficherHistorique(Joueur j) {
    	return super.liste("SELECT j from Joueur j WHERE id_joueur=?1",j);
    }
}
