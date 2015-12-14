package ovh.gorillahack.wazabi.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;

@Stateless
@Local(Dao.class)
@LocalBean
public class JoueurPartieDaoImpl extends DaoImpl<JoueurPartie>{

	public JoueurPartieDaoImpl() {
		super(JoueurPartie.class);
	}

	public List<Joueur> listerJoueurPartieCourante(){
		return null;
	}
}
