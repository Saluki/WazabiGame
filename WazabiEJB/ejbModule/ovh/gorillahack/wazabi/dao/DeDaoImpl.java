package ovh.gorillahack.wazabi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;

@Stateless
@Local(Dao.class)
@LocalBean
public class DeDaoImpl extends DaoImpl<De> {
	private static final long serialVersionUID = -1293913566498317290L;
	@PersistenceContext(unitName = "wazabi")
	private EntityManager entityManager;

	public DeDaoImpl() {
		super(De.class);
	}

	public List<De> getDes(Joueur j) {
		return /*
				 * super.liste("SELECT d FROM De d WHERE EXISTS " +
				 * "(SELECT jp FROM JoueurPartie jp WHERE jp.joueur = ?1" +
				 * " AND jp.partie = (SELECT p FROM Partie p WHERE p.id_partie = (SELECT MAX(p.id_partie))))"
				 * , j)
				 */ null;
	}

	public List<De> creerDes(int nombre) {
		ArrayList<De> des = new ArrayList<>();
		for (int i = 0; i < nombre; i++) {
			De de = new De();
			super.enregistrer(de);
		}
		return des;
	}
}
