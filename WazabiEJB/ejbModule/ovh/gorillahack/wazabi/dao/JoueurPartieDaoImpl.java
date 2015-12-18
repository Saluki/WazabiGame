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
		//return super.recherche("SELECT jp FROM JoueurPartie jp WHERE jp.ordre_joueur ="
		//		+ "(SELECT MIN(jp.ordre_joueur) FROM JoueurPartie jp WHERE "
		//		+ "jp.partie = (SELECT MAX(p.id_partie) FROM Partie p))");
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
	 * Joueur prochain dans le sens anti-horaire.
	 * 
	 * @param joueurPartie
	 * @return
	 */
	public JoueurPartie getJoueurPrecedent(JoueurPartie joueurPartie, Partie partie) {
		// lea requête JPQL ne fonctionnait pas

		List<JoueurPartie> list = partie.getJoueursParties();
		int precOrdre = joueurPartie.getOrdre_joueur() - 1;
		// on doit récupérer le dernier joueur
		if (precOrdre == -1) {
			JoueurPartie maxJoueur = list.get(0);
			int max = maxJoueur.getOrdre_joueur();
			for (JoueurPartie joueur : list) {
				if (joueur.getOrdre_joueur() > max) {
					maxJoueur = joueur;
					max = joueur.getOrdre_joueur();
				}
			}
			return maxJoueur;
		} else {
			for (JoueurPartie joueur : list) {
				if (joueur.getOrdre_joueur() == precOrdre) {
					return joueur;
				}
			}
		}
		return null;
	}

	public JoueurPartie getJoueurSuivant(JoueurPartie joueurPartie, Partie partie) {
		// la requête JPQL ne fonctionnait pas

		List<JoueurPartie> list = partie.getJoueursParties();
		int nextOrdre = joueurPartie.getOrdre_joueur() + 1;
		// on doit récupérer le dernier joueur et vérifier si on ne va pas eu
		// delà
		JoueurPartie maxJoueur = list.get(0);
		int max = maxJoueur.getOrdre_joueur();
		for (JoueurPartie joueur : list) {
			if (joueur.getOrdre_joueur() > max) {
				maxJoueur = joueur;
				max = joueur.getOrdre_joueur();
			}
		}
		if (nextOrdre > max) {
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
