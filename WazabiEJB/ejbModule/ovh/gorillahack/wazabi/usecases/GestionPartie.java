
package ovh.gorillahack.wazabi.usecases;

import java.util.List;

import javax.ejb.Remote;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;

@Remote
public interface GestionPartie {
	public Joueur inscrire(String pseudo, String motdepasse);
	public List<Partie> afficherHistorique(Joueur j);
	public Joueur seConnecter(String pseudo, String mdp);
	public Partie rejoindrePartie(Joueur j);
	public List<Joueur> listerJoueurPartieCourante();
	public Joueur commencerPartie();
	public List<De> lancerDe(Joueur j);
	public List<De> voirDe(Joueur j);
	public Carte piocherUneCarte(Joueur j);
	public void terminerTour();
}
