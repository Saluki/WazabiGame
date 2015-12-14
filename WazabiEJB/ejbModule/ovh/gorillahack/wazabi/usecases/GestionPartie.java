package ovh.gorillahack.wazabi.usecases;

import javax.ejb.Remote;

import ovh.gorillahack.wazabi.domaine.Joueur;

@Remote
public interface GestionPartie {
	public Joueur inscrire(String pseudo, String motdepasse);
}
