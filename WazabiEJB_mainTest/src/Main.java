import java.util.List;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Face.Valeur;
import ovh.gorillahack.wazabi.domaine.Partie.Status;
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;
import ovh.gorillahack.wazabi.exception.NotEnoughDiceException;
import ovh.gorillahack.wazabi.exception.ValidationException;
import ovh.gorillahack.wazabi.exception.XmlParsingException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

public class Main {
	public static void main(String[] args) throws ValidationException, NotEnoughDiceException {
		try {
			Context jndi = new InitialContext();
			GestionPartie gestionPartie = (GestionPartie) jndi
					.lookup("ejb:Wazabi/WazabiEJB/GestionPartieImpl!ovh.gorillahack.wazabi.usecases.GestionPartie");
			Joueur j1 = gestionPartie.inscrire("J1", "J1", "J1");
			Joueur j2 = gestionPartie.inscrire("J2", "J2", "J2");
			
			gestionPartie.creerPartie("partie");
			
			gestionPartie.rejoindrePartie(j1);
			gestionPartie.rejoindrePartie(j2);
			
			if(gestionPartie.getPartieCourante().getStatut()==Status.COMMENCE)
				System.out.println("PARTIE COMMENCEE");
			Scanner scan = new Scanner(System.in);
			while(scan.next().equals("ok")){
				System.out.println("Le joueur "+gestionPartie.getJoueurCourant().getPseudo()+" commence");
				List<De> des = gestionPartie.lancerDes(gestionPartie.getJoueurCourant());
				System.out.println("Valeurs de des obtenu: ");
				int cpt=0;
				for(De d: des){
					System.out.print(d.getValeur()+", ");
					if(d.getValeur()==Valeur.DE){
						cpt++;
					}
				}
				int [] joueurs = new int[cpt];
				for(int i = 0; i<cpt;i++){
					System.out.println("\nPassage du de au joueur "+gestionPartie.getJoueurSuivant(gestionPartie.getJoueurCourant(), gestionPartie.getPartieCourante().getSens()));
					joueurs[i] = gestionPartie.getJoueurSuivant(gestionPartie.getJoueurCourant(), gestionPartie.getPartieCourante().getSens()).getId_joueur();
					System.out.println("Et avec comme ID: "+joueurs[i]);
				}
				gestionPartie.donnerDes(gestionPartie.getJoueurCourant(), joueurs);
				System.out.println();
				List<Carte> cartes = gestionPartie.voirCartes(gestionPartie.getJoueurCourant());
				System.out.println("Cartes obtenues: ");
				for(Carte c: cartes){
					System.out.println(c.getId_carte()+ "-->"+c.getCodeEffet());
				}
				gestionPartie.terminerTour();
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
