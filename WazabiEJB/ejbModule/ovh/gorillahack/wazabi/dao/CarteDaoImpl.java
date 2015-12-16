package ovh.gorillahack.wazabi.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;

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

	public boolean piocherCarte(JoueurPartie joueur, Partie partie) {
		if (partie == null || joueur == null)
			throw new NullPointerException();
		// on sélectionne la carte avec l'ordre pioche minimal parmis toutes les
		// cartes de la pioche.
		Carte carte = super.rechercheLimit1("SELECT C FROM Carte C, Partie p WHERE p.id_partie = ?1 AND C MEMBER OF p.pioche "
				+ "ORDER BY c.ordre_pioche ASC", partie.getId_partie());
		boolean piochageReussi = partie.supprimerCarteDeLaPioche(carte);
		if (piochageReussi) {
			joueur.ajouterCarte(carte);
			return true;
		} else {
			return false;
		}

	}
}
