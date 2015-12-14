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
public class PartieDaoImpl extends DaoImpl<Partie>{
	
	public PartieDaoImpl() {
		super(Partie.class);
	}

	@PersistenceContext(unitName = "wazabi")
	private EntityManager entityManager;

	public Partie rejoindrePartie(Joueur j){
		return null;
	}	
	
	public List<Partie> afficherHistorique(Joueur j){
		return super.liste("SELECT p FROM Partie p WHERE EXISTS("
				+ "SELECT jp FROM JoueurPartie jp WHERE jp.joueur = ?1 AND jp.partie = p.id_partie)", j);
	}
}
