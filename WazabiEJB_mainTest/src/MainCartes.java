import java.util.List;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.domaine.Partie.Status;
import ovh.gorillahack.wazabi.exception.CardNotFoundException;
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;
import ovh.gorillahack.wazabi.exception.ValidationException;
import ovh.gorillahack.wazabi.exception.XmlParsingException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

public class MainCartes {

	public static void main(String[] args)
			throws ValidationException, XmlParsingException, NoCurrentGameException, CardNotFoundException {
		// TODO Auto-generated method stub
		try {
			Context jndi = new InitialContext();
			GestionPartie gestionPartie = (GestionPartie) jndi
					.lookup("ejb:Wazabi/WazabiEJB/GestionPartieImpl!ovh.gorillahack.wazabi.usecases.GestionPartie");
			Joueur joueur1 = gestionPartie.inscrire("test1", "test1", "test1");

			Joueur joueur3 = gestionPartie.inscrire("test3", "test3", "test3");

			gestionPartie.seConnecter("test1", "test1");
			gestionPartie.seConnecter("test3", "test3");

			Partie partie1 = gestionPartie.creerPartie("HELLO");
			System.out.println(partie1.getNom());
			gestionPartie.rejoindrePartie(joueur1);
			gestionPartie.rejoindrePartie(joueur3);

			if (gestionPartie.getPartieCourante().getStatut() == Status.COMMENCE)
				System.out.println("La partie commence!");
			System.out.println("Le joueur 1 " + gestionPartie.getJoueurCourant().getPseudo() + " commence");
			List<Carte> listeCarte = gestionPartie.voirCartes(joueur1);
			System.out.println("Le joueur 1 possede : " + listeCarte.size() + " cartes");
			for (int i = 0; i < listeCarte.size(); i++) {
				System.out.println("La " + i + " carte est " + listeCarte.get(i).getEffet());
			}
			System.out.println("---------------------------------------------------------------------");
			List<Carte> listeCarte2 = gestionPartie.voirCartes(joueur3);
			System.out.println("Le joueur 2 possede : " + listeCarte2.size() + " cartes");
			for (int i = 0; i < listeCarte2.size(); i++) {
				System.out.println("La " + i + " carte est " + listeCarte2.get(i).getEffet());
			}
			System.out.println("------------------------------------------------------------------");
			Scanner scanner = new Scanner(System.in);
			int numero = scanner.nextInt();
			// AUCUN, SENS, JOUEUR
			switch (listeCarte.get(numero).getInput().name()) {
			case "AUCUN":
				gestionPartie.utiliserCarte(listeCarte.get(numero).getId_carte());
				break;
			case "JOUEUR":
				List<JoueurPartie> listeJoueurPartie = gestionPartie.getPartieCourante().getJoueursParties();
				System.out.println("Veuillez choisir le joueur victime : ");
				for (int i = 0; i < listeJoueurPartie.size(); i++) {
					System.out.println("joueur numero " + i);
				}
				int numeroVictime = scanner.nextInt();
				gestionPartie.utiliserCarte(listeCarte.get(numero).getId_carte(),
						listeJoueurPartie.get(numeroVictime).getJoueur());
				break;
			case "SENS":
				int numSens;
				do {
					System.out.println("Voulez vous le faire aller dans le sens :");
					System.out.println("1 anti-horaire");
					System.out.println("2 horaire");
					numSens = scanner.nextInt();
				} while (numSens != 1 || numSens != 2);
				Sens sens;
				if(numSens == 1)
					sens = Partie.Sens.ANTIHORAIRE;
				else
					sens = Partie.Sens.HORAIRE;
				gestionPartie.utiliserCarte(listeCarte.get(numero).getId_carte(), sens);
			}
			System.out.println("Le joueur 1 a utiliser la carte numero "+numero);
			
			// etat du joueur 1 
			List<Carte> carteJoueur1 = gestionPartie.voirCartes(joueur1);
			List<Carte> carteJoueur2 = gestionPartie.voirCartes(joueur3);
			System.out.println("///////////////////////////////////////////////////////");
			System.out.println("/////////////   ETAT DU JEU ///////////////////////////");
			System.out.println("///////////////////////////////////////////////////////");
			System.out.println("Le joueur 1 possede : " + carteJoueur1.size() + " cartes");
			for (int i = 0; i < carteJoueur1.size(); i++) {
				System.out.println("La " + i + " carte est " + carteJoueur1.get(i).getEffet());
			}
			
			System.out.println("Le joueur1 possede : "+gestionPartie.voirDes(joueur1).size());
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Le joueur 2 possede : " + carteJoueur2.size() + " cartes");
			for (int i = 0; i < carteJoueur2.size(); i++) {
				System.out.println("La " + i + " carte est " + carteJoueur2.get(i).getEffet());
			}
			System.out.println("Le joueur possede : "+gestionPartie.voirDes(joueur3).size());
			System.out.println("-----------------------------------------------------------------");

		} catch (NamingException exception) {
			exception.printStackTrace();
		}
	}

}
