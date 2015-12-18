import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Status;
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;
import ovh.gorillahack.wazabi.exception.ValidationException;
import ovh.gorillahack.wazabi.exception.XmlParsingException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

public class MainTestDeconnection {
	public static void main(String[] args) throws ValidationException, NoCurrentGameException, XmlParsingException {
		try {
			Context jndi = new InitialContext();
			GestionPartie gestionPartie = (GestionPartie) jndi
					.lookup("ejb:Wazabi/WazabiEJB/GestionPartieImpl!ovh.gorillahack.wazabi.usecases.GestionPartie");
			Joueur joueur1 = gestionPartie.inscrire("test1", "test1", "test1");
			Joueur joueur3 = gestionPartie.inscrire("test3", "test3", "test3");

			gestionPartie.seConnecter("test1", "test1") ;
			gestionPartie.seConnecter("test3", "test3");


			Partie partie1 = gestionPartie.creerPartie("HELLO");
			System.out.println(partie1.getNom());
			gestionPartie.rejoindrePartie(joueur1);
			gestionPartie.rejoindrePartie(joueur3);
			
			if(gestionPartie.getPartieCourante().getStatut()==Status.COMMENCE)
				System.out.println("La partie commence!");
			System.out.println("Le joueur " + gestionPartie.getJoueurCourant().getPseudo() + " commence");
			gestionPartie.deconnecter(joueur1);
			System.out.println("Le joueur 1 est deconnecter  ");
			System.out.println("Le joueur "+ gestionPartie.getPartieCourante().getVainqueur().toString() +" gagne la partie");
			
		} catch (NamingException exception) {
			exception.printStackTrace();
		}
	}
}