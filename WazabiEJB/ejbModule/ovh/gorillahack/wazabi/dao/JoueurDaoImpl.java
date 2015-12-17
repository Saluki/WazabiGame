package ovh.gorillahack.wazabi.dao;

import java.util.List;
import java.util.Random;

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
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
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

	@EJB
	private CarteDaoImpl carteDaoImpl;
	@EJB
	private DeDaoImpl deDaoImpl;

	public JoueurDaoImpl() {
		super(Joueur.class);
	}

	public Joueur connecter(String pseudo, String motdepasse) {
		Joueur joueur = super.recherche("SELECT j FROM Joueur j " + "WHERE j.pseudo = ?1 AND j.mot_de_passe = ?2",
				pseudo, CryptService.hash(motdepasse));
		return joueur;
	}

	public Joueur inscrire(String pseudo, String motdepasse) {
		// On vérifie que le pseudo n'est pas déjà pris
		Joueur joueurExistant = super.recherche("SELECT j FROM Joueur j WHERE j.pseudo = ?1", pseudo);
		if (joueurExistant != null) {
			return null;
		} else {
			Joueur joueur = new Joueur(pseudo, CryptService.hash(motdepasse));
			enregistrer(joueur);
			return joueur;
		}
	}

	public List<De> lancerDes(Joueur j) {
		JoueurPartie jp = joueurPartieDaoImpl.getJoueurDeLaPartieCourante(j);
		List<De> des = jp.getDes();
		for (int i = 0; i < des.size(); i++) {
			De de = des.get(i);
			deDaoImpl.lancerDe(de);
		}
		return des;
	}

	public List<De> voirDes(Joueur j) {
		return deDaoImpl.getDes(j);
	}

	public List<Carte> voirCartes(Joueur j) {
		return carteDaoImpl.getCartes(j);
	}

	public Partie terminerTour() {
		JoueurPartie courant = joueurPartieDaoImpl.getJoueurCourant();
		Partie p = partieDaoImpl.getPartieCourante();
		if (courant.getDes() == null) { 
		} else if (courant.getDes().isEmpty()) {
			System.out.println("Le joueur " + courant.getJoueur().getPseudo() + " a gagné car il n'a plus de dés");
			p.setStatut(Status.PAS_COMMENCE);
			p.setVainqueur(courant.getJoueur());
			p = partieDaoImpl.mettreAJour(p);
		} else {
			System.out.println("prochain tour, courant = "+ p.getCourant());
			JoueurPartie suivant = null;
			if (p.getSens() == Sens.HORAIRE) {
				suivant = joueurPartieDaoImpl.getJoueurSuivant(courant, p);
				p.setCourant(suivant);
				p = partieDaoImpl.mettreAJour(p);
			} else if (p.getSens() == Sens.ANTIHORAIRE) {
				suivant = joueurPartieDaoImpl.getJoueurPrecedent(courant, p);
				p.setCourant(suivant);
				p = partieDaoImpl.mettreAJour(p);
			}
			System.out.println("prochain tour, courant = "+ p.getCourant());
			System.out.println("prochain tour, le suivant était :" + suivant);
			//courant.setOrdre_joueur(PartieDaoImpl.ordre++);
			//joueurPartieDaoImpl.mettreAJour(courant);
			//p.setCourant(joueurPartieDaoImpl.getJoueurCourant());
		}
		return p;
	}

	public void deconnecter(Joueur j, int nombreJoueursMin) {
		Partie p = partieDaoImpl.getPartieCourante();
		JoueurPartie jp = joueurPartieDaoImpl.getJoueurDeLaPartieCourante(j);
		joueurPartieDaoImpl.enleverJoueur(jp);
		List<JoueurPartie> temp = partieDaoImpl.getPartieCourante().getJoueursParties();
		int nbJoueursRestants = 0;
		for (JoueurPartie jop : temp) {
			if (jop.estActif())
				nbJoueursRestants++;
		}
		if (nbJoueursRestants < nombreJoueursMin) {
			p.setStatut(Status.ANNULEE);
			partieDaoImpl.mettreAJour(p);
		}
	}

	public List<Joueur> listerJoueurPartieCourante() {
		return super.liste("SELECT j FROM Joueur j WHERE EXISTS " + "(SELECT jp FROM JoueurPartie jp WHERE "
				+ "jp.partie = (SELECT MAX(p.id_partie) FROM Partie p)" + "AND jp.joueur = j.id_joueur)");
	}

	public Carte piocherCarte(Joueur j) {
		Partie p = partieDaoImpl.getPartieCourante();
		List<Carte> pioche = p.getPioche();
		Random rand = new Random();
		Carte c = pioche.remove(rand.nextInt(pioche.size()-1));
		JoueurPartie jp = joueurPartieDaoImpl.getJoueurDeLaPartieCourante(j);
		List<Carte> cartes = jp.getCartes();
		cartes.add(c);
		jp.setCartes(cartes);
		joueurPartieDaoImpl.enregistrer(jp);
		partieDaoImpl.enregistrer(p);
		return c;
	}
	
	public Carte remettreCarte(Joueur j, Carte carte) {
		Partie p = partieDaoImpl.getPartieCourante();
		List<Carte> pioche = p.getPioche();
		JoueurPartie jp = joueurPartieDaoImpl.getJoueurDeLaPartieCourante(j);
		List<Carte> cartesJoueur = jp.getCartes();
		cartesJoueur.remove(carte);
		//jp.setCartes(cartesJoueur);
		pioche.add(carte);
		//p.setPioche(pioche);
		joueurPartieDaoImpl.enregistrer(jp);
		partieDaoImpl.enregistrer(p);
		return carte;
	}
}
