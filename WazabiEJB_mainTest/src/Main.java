import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.JoueurPartie;
import ovh.gorillahack.wazabi.domaine.Partie;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.domaine.Partie.Status;
import ovh.gorillahack.wazabi.usecases.GestionPartie;

public class Main {
	public static void main(String[] args) {
		try {
			Context jndi = new InitialContext();
			GestionPartie gestionPartie = (GestionPartie) jndi
					.lookup("ejb:Wazabi/WazabiEJB/GestionPartieImpl!ovh.gorillahack.wazabi.usecases.GestionPartie");
			Joueur joueur1 = gestionPartie.inscrire("test1", "test1");
			Joueur joueur2 = gestionPartie.inscrire("test2", "test2");
			ArrayList<Carte> cartes = new ArrayList<>();
			cartes.add(new Carte(0, 1, null, 0));
			cartes.add(new Carte(2, 3, null, 1));
			Partie partie1 = gestionPartie.enregistrerPartie("partie1", new Date(), Sens.HORAIRE, joueur1, null, null,
					Status.PAS_COMMENCE);
			if (joueur1 != null) {
				JoueurPartie joueurPartie1 = gestionPartie.enregistrerJoueurPartie(joueur1, partie1);
				partie1.setCourant(joueurPartie1);
			}
			if (joueur2 != null) {
				JoueurPartie joueurPartie2 = gestionPartie.enregistrerJoueurPartie(joueur2, partie1);
			}
			partie1.setCartes(cartes);
			

			for (Partie p : gestionPartie.afficherHistorique(joueur1)) {
				System.out.println(p.getNom());
			}

			System.out.println("test1 test de connexion : " + ((gestionPartie.seConnecter("test1", "test1") != null)
					? "Connexion réussie" : "Connexion ratée"));
			System.out.println(
					"test1 test de connexion (doit rater) : " + ((gestionPartie.seConnecter("test1", "test2") != null)
							? "Connexion réussie" : "Connexion ratée"));
			System.out.println(
					"test1 test d'inscription (doit rater) : " + ((gestionPartie.inscrire("test1", "test1") != null)
							? "Inscription réussie" : "Inscription ratée"));
		} catch (NamingException exception) {
			exception.printStackTrace();
		}
	}
}
