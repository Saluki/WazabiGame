package ovh.gorillahack.wazabi.dao;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ovh.gorillahack.wazabi.domaine.JoueurPartie;

@SuppressWarnings("serial")
@Stateless
@Local(Dao.class)
@LocalBean
public class JoueurPartieDaoImpl extends DaoImpl<JoueurPartie>{

	public JoueurPartieDaoImpl() {
		super(JoueurPartie.class);
	}
	
	public JoueurPartie getJoueurCourant(){
		return super.recherche("SELECT jp FROM JoueurPartie jp WHERE jp.ordre_joueur ="
				+ "(SELECT MIN(jp.ordre_joueur) FROM JoueurPartie jp WHERE "
				+ "jp.partie = (SELECT MAX(p.id_partie) FROM Partie p))");
	}
}
