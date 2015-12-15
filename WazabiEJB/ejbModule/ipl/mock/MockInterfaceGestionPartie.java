package ipl.mock;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.ejb.Remote;

import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;

@Remote
public interface MockInterfaceGestionPartie {

	public boolean verificationPseudo(String pseudo);
	public boolean inscription(String pseudo,String mdp);
	public boolean verificationAuthentification(String pseudo,String mdp);
	public Stack pileInformation();
	EtatPartie etatPartie();
	public boolean createPartie(String partie);
	public  List<De> lancerDes(Joueur joueur);
	public Map<String, String> carteJouer(int carte);
}
