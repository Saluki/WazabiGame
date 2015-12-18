package ovh.gorillahack.wazabi.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ovh.gorillahack.wazabi.domaine.Face;

@SuppressWarnings("serial")
@Stateless
@Local(Dao.class)
@LocalBean
public class FaceDaoImpl extends DaoImpl<Face>{
	@PersistenceContext(unitName = "wazabi")
	private EntityManager entityManager;
	
    public FaceDaoImpl() {
    	super(Face.class);
    }
    
    public List<Face> getAllFaces() {
    	return super.liste("SELECT f FROM Face f");
    }
    
    public List<Face> enregistrer(List<Face> faces){
    	for(Face f: faces)
    		f = enregistrer(f);
    	return faces;
    }
}