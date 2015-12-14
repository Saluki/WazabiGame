package ovh.gorillahack.wazabi.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.util.CryptService;

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

	public Joueur connecter(String pseudo, String motdepasse) {
		Joueur joueur = super.recherche("SELECT j FROM Joueur j " + "WHERE j.pseudo = ?1 AND j.mot_de_passe = ?2", pseudo,
				CryptService.hash(motdepasse));
		return joueur;
	}

	public Joueur inscrire(String pseudo, String motdepasse) {
		// On v�rifie que le pseudo n'est pas d�j� pris
		Joueur joueurExistant = super.recherche("SELECT j FROM Joueur j WHERE j.pseudo = ?1", pseudo);
		if (joueurExistant != null) {
			return null;
		} else {
			Joueur joueur = new Joueur(pseudo, CryptService.hash(motdepasse));
			enregistrer(joueur);
			return joueur;
		}
	}
	
	public Carte piocherCarte(Joueur j) {
		return null;
	}
	
	public List<De> lancerDes(Joueur j){
		return null;
	}
	
	public List<De> voirDes(Joueur j){
		return null;
	}
	
	public List<Joueur> listerJoueurPartieCourante() {
		return super.liste("SELECT j FROM Joueur j WHERE EXISTS "
				+ "(SELECT jp FROM JoueurPartie jp WHERE "
				+ "jp.partie = (SELECT MAX(p.id_partie) FROM Partie p)"
				+ "AND jp.joueur = j.id_joueur)");
	}
	
	public Joueur commencerPartie() {
		//TODO Il faut: Initialiser la pioche, changer le status de la partie, attribuer les d�s aux joueurs?, attribuer les cartes aux joueurs, s�lectionner le joueur courant
		return null;
	}
}
