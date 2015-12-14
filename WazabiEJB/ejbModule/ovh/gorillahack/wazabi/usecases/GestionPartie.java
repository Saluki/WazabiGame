package ovh.gorillahack.wazabi.usecases;

import java.util.Dictionary;
import java.util.Map;
import java.util.Vector;

import javax.ejb.Remote;

import ovh.gorillahack.wazabi.domaine.Joueur;

@Remote
public interface GestionPartie {
	public Joueur inscrire(String pseudo, String motdepasse);


	boolean verificationAuthentification(String pseudo, String mdp);
	

}
