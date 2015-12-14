package ovh.gorillahack.wazabi.dao;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ovh.gorillahack.wazabi.domaine.Joueur;

/**
 * Session Bean implementation class JoueurDaoImpl
 */
@Stateless
@Local(Dao.class)
@LocalBean
public class JoueurDaoImpl extends DaoImpl<Joueur> {
	private static final long serialVersionUID = -6188714066284889331L;
	@PersistenceContext(unitName = "wazabi")
	private EntityManager entityManager;

	public JoueurDaoImpl() {
		super(Joueur.class);
	}

	/**
	 * 
	 * @param pseudo
	 *            Le pseudo à vérifier
	 * @param motdepasse
	 *            Le mot de passe à vérifier
	 * @return Le joueur si l'authentification a réussi, null sinon.
	 */
	public Joueur connecter(String pseudo, String motdepasse) {
		Joueur joueur = super.recherche("SELECT j FROM Joueur j " + "WHERE j.pseudo = ?1 AND j.mot_de_passe = ?2", pseudo,
				motdepasse);
		return joueur;
	}

	/**
	 * 
	 * @param pseudo
	 *            Le pseudo du compte qu'on souhaite créer
	 * @param motdepasse
	 *            Le mot de passe du compte qu'on souhaite créer
	 * @return Le joueur créé si l'inscription a réussi, null si l'inscription a
	 *         échoué (ex : pseudo existe déjà)
	 */
	public Joueur inscrire(String pseudo, String motdepasse) {
		// On vérifie que le pseudo n'est pas déjà pris
		Joueur joueurExistant = super.recherche("SELECT j FROM Joueur j WHERE j.pseudo = ?1", pseudo);
		if (joueurExistant != null) {
			return null;
		} else {
			Joueur joueur = new Joueur(pseudo, motdepasse);
			enregistrer(joueur);
			return joueur;
		}

	}
}
