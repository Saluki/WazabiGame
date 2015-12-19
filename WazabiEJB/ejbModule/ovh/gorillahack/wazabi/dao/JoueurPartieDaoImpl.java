package ovh.gorillahack.wazabi.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.exception.NotEnoughDiceException;

@SuppressWarnings("serial")
@Stateless
@Local(Dao.class)
@LocalBean
public class JoueurPartieDaoImpl extends DaoImpl<JoueurPartie> {

	@EJB
	private PartieDaoImpl partieDaoImpl;

	public JoueurPartieDaoImpl() {
		super(JoueurPartie.class);
	}

	public JoueurPartie getJoueurCourant() {
		return partieDaoImpl.getPartieCourante().getCourant();
	}

	public JoueurPartie getJoueurDeLaPartieCourante(Joueur j) {
		return super.recherche("SELECT jp FROM JoueurPartie jp WHERE jp.joueur = ?1" + " AND jp.partie=?2", j,
				partieDaoImpl.getPartieCourante());
	}

	public void enleverJoueur(JoueurPartie j) {
		j.setEstActif(false);
		super.mettreAJour(j);
	}

	public void setDes(Joueur j, List<De> des) {
		JoueurPartie jp = getJoueurDeLaPartieCourante(j);
		jp.setDes(des);
		super.enregistrer(jp);
	}

	public boolean ajouterCarte(JoueurPartie joueur, Carte carte) {
		return joueur.ajouterCarte(carte);
	}

	public void supprimerDe(JoueurPartie joueur) throws NotEnoughDiceException {
		joueur.supprimerDe();
	}

	/**
	 * Renvoie le joueur prochain dans le sens anti-horaire.
	 * 
	 * @param joueurPartie
	 * @return
	 */
	public JoueurPartie getJoueurPrecedent(JoueurPartie joueurPartie, Partie partie) {
		// la requête JPQL ne fonctionnait pas -> méthode bricolage

		List<JoueurPartie> list = partie.getJoueursParties();
		// le joueur precedent a l'ordre du joueur courant - 1
		int precOrdre = joueurPartie.getOrdre_joueur() - 1;
		if (precOrdre == -1) {
			// on doit récupérer le dernier joueur (ordre = max)
			JoueurPartie maxJoueur = list.get(0);
			int max = maxJoueur.getOrdre_joueur();
			for (JoueurPartie joueur : list) {
				// on cherche le max
				if (joueur.getOrdre_joueur() > max) {
					maxJoueur = joueur;
					max = joueur.getOrdre_joueur();
				}
			}
			return maxJoueur;
		} else {
			for (JoueurPartie joueur : list) {
				// on cherche juste le joueur avec le bon ordre
				if (joueur.getOrdre_joueur() == precOrdre) {
					return joueur;
				}
			}
		}
		return null;
	}

	/**
	 * Renvoie le joueur suivant, pour le sens antihoraire
	 * @param joueurPartie
	 * @param partie
	 * @return
	 */
	public JoueurPartie getJoueurSuivant(JoueurPartie joueurPartie, Partie partie) {
		// la requete JPQL ne fonctionnait pas -> methode bricolage
		// pourrait etre fortement amelioree

		List<JoueurPartie> list = partie.getJoueursParties();
		int nextOrdre = joueurPartie.getOrdre_joueur() + 1;
		// on doit recuperer le dernier joueur et vérifier si on ne va pas au
		// dela
		JoueurPartie maxJoueur = list.get(0);
		int max = maxJoueur.getOrdre_joueur();
		for (JoueurPartie joueur : list) {
			// on cherche le max
			if (joueur.getOrdre_joueur() > max) {
				maxJoueur = joueur;
				max = joueur.getOrdre_joueur();
			}
		}
		if (nextOrdre > max) {
			// si il n'y a pas plus grand, on revient au premier
			nextOrdre = 0;
		}

		for (JoueurPartie joueur : list) {
			if (joueur.getOrdre_joueur() == nextOrdre) {
				return joueur;
			}
		}

		return null;
	}
}
