import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Status;
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;
import ovh.gorillahack.wazabi.exception.ValidationException;
import ovh.gorillahack.wazabi.exception.XmlParsingException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

public class Main {
	public static void main(String[] args) throws ValidationException {
		try {
			Context jndi = new InitialContext();
			GestionPartie gestionPartie = (GestionPartie) jndi
					.lookup("ejb:Wazabi/WazabiEJB/GestionPartieImpl!ovh.gorillahack.wazabi.usecases.GestionPartie");
			Joueur joueur1 = gestionPartie.inscrire("test1", "test1", "test1");
			Joueur joueur2 = gestionPartie.inscrire("test2", "test2", "test2");
			Joueur joueur3 = gestionPartie.inscrire("test3", "test3", "test3");

			System.out.println("test1 test de connexion : " + ((gestionPartie.seConnecter("test1", "test1") != null)
					? "Connexion réussie" : "Connexion ratée"));
			System.out.println(
					"test1 test de connexion (doit rater) : " + ((gestionPartie.seConnecter("test1", "test2") != null)
							? "Connexion réussie" : "Connexion ratée"));
			System.out.println("test1 test d'inscription (doit rater) : "
					+ ((gestionPartie.inscrire("test1", "test1", "test1") != null) ? "Inscription réussie"
							: "Inscription ratée"));

			Partie partie1 = gestionPartie.creerPartie("HELLO");
			System.out.println(partie1.getNom());
			gestionPartie.rejoindrePartie(joueur1);
			gestionPartie.rejoindrePartie(joueur1);
			gestionPartie.rejoindrePartie(joueur3);

			System.out.println("Historique des parties de joueur1 (partie1): ");
			for (Partie p : gestionPartie.afficherHistorique(joueur1)) {
				System.out.println(p.getNom());
			}

			System.out.println("Historique des parties de joueur2 (partie1): ");
			for (Partie p : gestionPartie.afficherHistorique(joueur2)) {
				System.out.println(p.getNom());
			}

			System.out.println("Joueurs acutellement en attente dans un salon: ");
			for (Joueur j : gestionPartie.listerJoueurPartieCourante()) {
				System.out.println(j.getPseudo());
			}
			if(gestionPartie.getPartieCourante().getStatut()==Status.COMMENCE)
				System.out.println("La partie commence!");
			System.out.println("Le joueur " + gestionPartie.getJoueurCourant().getPseudo() + " commence");
			
			Carte c = gestionPartie.piocherUneCarte(gestionPartie.getJoueurCourant());
			System.out.println("Carte tiree: " +c.getId_carte());
			/*
			 * List<De> des = gestionPartie.voirDes(joueur1); for(De de: des){
			 * System.out.println(de.getId_de()); }
			 
			gestionPartie.terminerTour();
			System.out.println("C'est au tour de " + gestionPartie.getJoueurCourant().getPseudo() + " de joueur");
			System.out.println("Le joueur 2 pioche une carte : " + gestionPartie.piocherUneCarte(joueur2));
			Carte carte = gestionPartie.piocherUneCarte(joueur3);
			System.out.println("Le joueur 3 pioche une carte : " + carte);
			//System.out.println("Le joueur 3 remet cette carte dans la pioche : " + gestionPartie.remettreCarte(joueur3, carte));
			System.out.println("le joueur 1 lance ses dés : " + gestionPartie.lancerDes(joueur1));
			System.out.println("le joueur 1 lance ses dés : " + gestionPartie.lancerDes(joueur1));
			
			//Carte carte = gestionPartie.voirCartes(joueur3).get(0);
			//System.out.println("Le joueur 3 retourne une carte : " + carte.getCodeEffet() + gestionPartie.retournerCarteDansLaPioche(joueur3, carte));
			*/
			List<De> des = gestionPartie.lancerDes(gestionPartie.getJoueurCourant());
			for(De d: des){
				System.out.println("De obtenu (J1): "+d.getValeur());
			}
			gestionPartie.terminerTour();
			des = gestionPartie.lancerDes(gestionPartie.getJoueurCourant());
			for(De d: des){
				System.out.println("De obtenu (J2): "+d.getValeur());
			}
		} catch (NamingException exception) {
			exception.printStackTrace();
		} catch (XmlParsingException e) {
			e.printStackTrace();
		} catch (NoCurrentGameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
