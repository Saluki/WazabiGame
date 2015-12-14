package ovh.gorillahack.wazabi.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;

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
}
