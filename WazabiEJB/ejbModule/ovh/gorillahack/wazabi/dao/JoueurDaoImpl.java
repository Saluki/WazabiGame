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
	 *            Le pseudo � v�rifier
	 * @param motdepasse
	 *            Le mot de passe � v�rifier
	 * @return Le joueur si l'authentification a r�ussi, null sinon.
	 */
	public Joueur connecter(String pseudo, String motdepasse) {
		Joueur joueur = super.recherche("SELECT j FROM Joueur j " + "WHERE j.pseudo = ?1 AND j.mot_de_passe = ?2", pseudo,
				motdepasse);
		return joueur;
	}

	/**
	 * 
	 * @param pseudo
	 *            Le pseudo du compte qu'on souhaite cr�er
	 * @param motdepasse
	 *            Le mot de passe du compte qu'on souhaite cr�er
	 * @return Le joueur cr�� si l'inscription a r�ussi, null si l'inscription a
	 *         �chou� (ex : pseudo existe d�j�)
	 */
	public Joueur inscrire(String pseudo, String motdepasse) {
		// On v�rifie que le pseudo n'est pas d�j� pris
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
