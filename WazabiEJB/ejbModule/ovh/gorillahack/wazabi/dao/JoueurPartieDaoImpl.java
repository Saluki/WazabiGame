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
import ovh.gorillahack.wazabi.exception.QueryException;

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
		return super.recherche("SELECT jp FROM JoueurPartie jp WHERE jp.ordre_joueur ="
				+ "(SELECT MIN(jp.ordre_joueur) FROM JoueurPartie jp WHERE "
				+ "jp.partie = (SELECT MAX(p.id_partie) FROM Partie p))");
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

	// TODO : il n'y a qu'un seul joueur
	/**
	 * Joueur prochain dans le sens anti-horaire.
	 * 
	 * @param joueurPartie
	 * @return
	 */
	public JoueurPartie getJoueurPrecedent(JoueurPartie joueurPartie, Partie partie) {
		// on pourrait le faire mieux en limitant la query à 3 res : précédent,
		// lui et suivant mais ce serait plus complexe

		List<JoueurPartie> list = super.liste(
				"SELECT jp FROM JoueurPartie jp, Partie p WHERE jp IN (SELECT  p AND p.id_partie = ?1 ORDER BY jp.ordre_joeur ASC",
				partie.getId_partie());
		int index = list.indexOf(joueurPartie);
		int precIndex = index - 1;
		if (precIndex == -1) {
			precIndex = list.size() - 1;
		}
		JoueurPartie joueurPrecedent = list.get(precIndex);
		return joueurPrecedent;

	}
}
