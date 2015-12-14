
package ovh.gorillahack.wazabi.usecases;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.domaine.Partie.Status;

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
    public Partie enregistrerPartie(String nom, Date timestamp, Sens sens, Joueur vainqueur, List<Carte> cartes,
			JoueurPartie courant, Status statut);
    public JoueurPartie enregistrerJoueurPartie(Joueur j, Partie partie);
}
