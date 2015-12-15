package ovh.gorillahack.wazabi.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;

@SuppressWarnings("serial")
@Stateless
@Local(Dao.class)
@LocalBean
public class JoueurPartieDaoImpl extends DaoImpl<JoueurPartie>{

	@EJB
	private PartieDaoImpl partieDaoImpl;
	
	public JoueurPartieDaoImpl() {
		super(JoueurPartie.class);
	}
	
	public JoueurPartie getJoueurCourant(){
		return super.recherche("SELECT jp FROM JoueurPartie jp WHERE jp.ordre_joueur ="
				+ "(SELECT MIN(jp.ordre_joueur) FROM JoueurPartie jp WHERE "
				+ "jp.partie = (SELECT MAX(p.id_partie) FROM Partie p))");
	}
	
	public JoueurPartie getJoueurDeLaPartieCourante(Joueur j){
		return super.recherche("SELECT jp FROM JoueurPartie jp WHERE jp.joueur = ?1"
				+ " AND jp.partie=?2", j, partieDaoImpl.getPartieCourante());
	}
	
	public void enleverJoueur(JoueurPartie j){
		j.setEstActif(false);
		super.mettreAJour(j);
	}
	
	public void setDes(Joueur j, List<De> des){
		JoueurPartie jp = getJoueurDeLaPartieCourante(j);
		jp.setDes(des);
		super.enregistrer(jp);
	}
}
