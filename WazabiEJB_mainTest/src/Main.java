import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.exception.ValidationException;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

public class Main {
	public static void main(String[] args) throws ValidationException {
		try {
			Context jndi = new InitialContext();
			GestionPartie gestionPartie = (GestionPartie) jndi
					.lookup("ejb:Wazabi/WazabiEJB/GestionPartieImpl!ovh.gorillahack.wazabi.usecases.GestionPartie");
			Joueur joueur1 = gestionPartie.inscrire("test1", "test1","test1");
			Joueur joueur2 = gestionPartie.inscrire("test2", "test2","test1");
			
			System.out.println("test1 test de connexion : " + ((gestionPartie.seConnecter("test1", "test1") != null)
					? "Connexion réussie" : "Connexion ratée"));
			System.out.println("test1 test de connexion (doit rater) : " + ((gestionPartie.seConnecter("test1", "test2") != null)
					? "Connexion réussie" : "Connexion ratée"));
			System.out.println(
					"test1 test d'inscription (doit rater) : " + ((gestionPartie.inscrire("test1", "test1","test1") != null)
							? "Inscription réussie" : "Inscription ratée"));
			
			//ArrayList<Carte> cartes = new ArrayList<>();
			//cartes.add(new Carte(0, 1, null, 0));
			//cartes.add(new Carte(2, 3, null, 1));
			Partie partie1 = gestionPartie.creerPartie("HELLO");
			System.out.println(partie1.getNom());
			gestionPartie.rejoindrePartie(joueur1);
			gestionPartie.rejoindrePartie(joueur2);
			
			System.out.println("Historique des parties de joueur1 (partie1): ");
			for (Partie p : gestionPartie.afficherHistorique(joueur1)) {
				System.out.println(p.getNom());
			}
			
			System.out.println("Historique des parties de joueur2 (partie1): ");
			for (Partie p : gestionPartie.afficherHistorique(joueur1)) {
				System.out.println(p.getNom());
			}
			
			System.out.println("Joueurs acutellement en attente dans un salon: ");
			for(Joueur j: gestionPartie.listerJoueurPartieCourante()){
				System.out.println(j.getPseudo());
			}
			
			System.out.println("Le joueur " + gestionPartie.getJoueurCourant().getPseudo()
					+ "commence");
			gestionPartie.commencerPartie();
		} catch (NamingException exception) {
			exception.printStackTrace();
		}
	}
}
