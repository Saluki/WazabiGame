package ovh.gorillahack.wazabi.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Status;
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
	
	@EJB
	private PartieDaoImpl partieDaoImpl;
	
	@EJB
	private JoueurPartieDaoImpl joueurPartieDaoImpl;

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
		Partie p = partieDaoImpl.getPartieCourante();
		if(p==null||p.getStatut()==Status.PAS_COMMENCE||p.getStatut()==Status.COMMENCE)
			return null;
		for(Joueur j: listerJoueurPartieCourante()){
			//TODO Mettre 4 d�s pour chaque joueur
			/*TODO Mettre 3 cartes pour chaque joueur. 2 solutions:
			Soit on initialise d'abord la pile avec toutes les cartes, et on fait piocher chaque joueur 3 fois d'affil�e
			Soit on donne d'abord les 3 cartes � chaque joueur, puis on initialise la pioche avec le reste.*/
			p.setStatut(Status.COMMENCE);
			partieDaoImpl.mettreAJour(p);
		}
		
		
		//TODO d�finir le joueur courant
		p.setCourant(joueurPartieDaoImpl.getJoueurCourant());
		partieDaoImpl.mettreAJour(p);
		return joueurPartieDaoImpl.getJoueurCourant().getJoueur();
	}
	
	public void terminerTour(){
		JoueurPartie courant = joueurPartieDaoImpl.getJoueurCourant();
		Partie p = courant.getPartie();
		if(courant.getDes() == null){
		} else if(courant.getDes().isEmpty()){
			System.out.println("Le joueur " + courant.getJoueur().getPseudo()+" a gagn� car il n'a plus de d�s");
			p.setStatut(Status.PAS_COMMENCE);
			p.setVainqueur(courant.getJoueur());
			partieDaoImpl.mettreAJour(p);
			joueurPartieDaoImpl.mettreAJour(courant);
		} else{
			courant.setOrdre_joueur(PartieDaoImpl.ordre++);
			joueurPartieDaoImpl.mettreAJour(courant);
		}
	}
}
