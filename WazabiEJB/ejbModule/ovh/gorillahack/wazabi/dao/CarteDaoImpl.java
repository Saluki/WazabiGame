package ovh.gorillahack.wazabi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.CarteEffet;
import ovh.gorillahack.wazabi.domaine.Joueur;

@Stateless
@Local(Dao.class)
@LocalBean
public class CarteDaoImpl extends DaoImpl<Carte> {
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
		return /*
				 * super.liste("SELECT c FROM Carte c " +
				 * "WHERE EXISTS (SELECT jp FROM JoueurPartie jp WHERE jp.joueur = ?1"
				 * +
				 * "AND jp.partie = (SELECT p FROM Partie p WHERE p.id_partie = (SELECT MAX(p.id_partie))))"
				 * ,j)
				 */ null;
	}

	public List<Carte> creerCartes(int cout, CarteEffet carteEffet, int nombre) {
		ArrayList<Carte> list = new ArrayList<>();
		for (int i = 0; i < nombre; i++) {
			Carte carte = new Carte(carteEffet, cout);
			super.enregistrer(carte);
			list.add(carte);
		}
		return list;
	}
}
