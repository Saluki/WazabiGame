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
			while (true) {
				List<Carte> listeCarte = gestionPartie.voirCartes(joueur1);
				System.out.println("Le joueur 1 possede : " + listeCarte.size() + " cartes");
				for (int i = 0; i < listeCarte.size(); i++) {
					System.out.println("La " + i + " carte est " + listeCarte.get(i).getEffet());
				}
				System.out.println("Le joueur possede : " + gestionPartie.voirDes(joueur1).size()+"des");
				System.out.println("---------------------------------------------------------------------");
				List<Carte> listeCarte2 = gestionPartie.voirCartes(joueur3);
				System.out.println("Le joueur 2 possede : " + listeCarte2.size() + " cartes");
				for (int i = 0; i < listeCarte2.size(); i++) {
					System.out.println("La " + i + " carte est " + listeCarte2.get(i).getEffet());
				}
				System.out.println("Le joueur possede : " + gestionPartie.voirDes(joueur3).size()+"des");
				System.out.println("------------------------------------------------------------------");
				Joueur joueurCourant = gestionPartie.getJoueurCourant();
				List<Carte> carteJoueurCourant = gestionPartie.voirCartes(joueurCourant);
				System.out.println("c'est au tour de "+joueurCourant.getPseudo());
				
				Scanner scanner = new Scanner(System.in);
				int numero = scanner.nextInt();
				if (numero == -1) {
					gestionPartie.piocherUneCarte(joueur1);
					continue;
				} else if (numero == -2) {
					break;
				}
		
				// AUCUN, SENS, JOUEUR
				switch (carteJoueurCourant.get(numero).getInput().name()) {
				case "AUCUN":
					gestionPartie.utiliserCarte(carteJoueurCourant.get(numero).getId_carte());
					break;
				case "JOUEUR":
					List<JoueurPartie> listeJoueurPartie = gestionPartie.getPartieCourante().getJoueursParties();
					System.out.println("Veuillez choisir le joueur victime : ");
					for (int i = 0; i < listeJoueurPartie.size(); i++) {
						System.out.println("joueur numero " + i);
					}
					int numeroVictime = scanner.nextInt();
					gestionPartie.utiliserCarte(carteJoueurCourant.get(numero).getId_carte(),
							listeJoueurPartie.get(numeroVictime).getJoueur());
					break;
				case "SENS":
					int numSens;
					do {
						System.out.println("Voulez vous le faire aller dans le sens :");
						System.out.println("1 anti-horaire");
						System.out.println("2 horaire");
						numSens = scanner.nextInt();
						System.out.println("numero du sens : "+numSens);
					} while (numSens != 1 && numSens != 2);
					Sens sens;
					if (numSens == 1)
						sens = Partie.Sens.ANTIHORAIRE;
					else
						sens = Partie.Sens.HORAIRE;
					gestionPartie.utiliserCarte(carteJoueurCourant.get(numero).getId_carte(), sens);
					break;
				}
				
				
				System.out.println("Le joueur "+joueurCourant.getPseudo()+" a utiliser la carte " + carteJoueurCourant.get(numero).getId_carte());
				
				gestionPartie.terminerTour();

			// etat du joueur 1
			gestionPartie.voirCartes(joueur1);
			gestionPartie.voirCartes(joueur3);
			System.out.println("///////////////////////////////////////////////////////");
			System.out.println("/////////////   TOUR SUIVANT///////////////////////////");
			System.out.println("///////////////////////////////////////////////////////");
			scanner.close();
			}
			
		} catch (NamingException exception) {
			exception.printStackTrace();
		}
	}

}
