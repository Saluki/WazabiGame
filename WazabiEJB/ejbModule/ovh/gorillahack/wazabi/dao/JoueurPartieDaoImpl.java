package ovh.gorillahack.wazabi.dao;

import java.util.List;

import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;

public class JoueurPartieDaoImpl extends DaoImpl<JoueurPartie>{

	public JoueurPartieDaoImpl() {
		super(JoueurPartie.class);
	}

	public List<Joueur> listerJoueurPartieCourante(){
		return null;
	}
}
