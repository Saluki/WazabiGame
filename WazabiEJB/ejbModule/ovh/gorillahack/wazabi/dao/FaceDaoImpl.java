package ovh.gorillahack.wazabi.dao;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ovh.gorillahack.wazabi.domaine.Face;

@Stateless
@Local(Dao.class)
@LocalBean
public class FaceDaoImpl extends DaoImpl<Face>{
	@PersistenceContext(unitName = "wazabi")
	private EntityManager entityManager;
	
    public FaceDaoImpl() {
    	super(Face.class);
    }
}