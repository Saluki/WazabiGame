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
import ovh.gorillahack.wazabi.usecases.GestionPartie;

public class Main {
	public static void main(String[] args) {
		try {
			Context jndi = new InitialContext();
			GestionPartie gestionPartie = (GestionPartie) jndi.lookup("ejb:Wazabi/WazabiEJB/GestionPartieImpl!ovh.gorillahack.wazabi.usecases.GestionPartie");
			Joueur joueur1 = new Joueur("test1", "test1");
			Joueur joueur2 = new Joueur("test2", "test2");
			ArrayList<Carte> cartes = new ArrayList<>();
			cartes.add(new Carte(0, 1, null, 0));
			cartes.add(new Carte(2, 3, null, 1));
			Partie partie1 = new Partie("partie1", new Date(), Sens.HORAIRE, joueur1, null, null);
			JoueurPartie joueurPartie1 = new JoueurPartie(0, 0);
			partie1.setCartes(cartes);
			partie1.setCourant(joueurPartie1);

			gestionPartie.inscrire("test1", "test1");
			gestionPartie.inscrire("test2", "test2");
		} catch (NamingException exception) {
			exception.printStackTrace();
		}
	}
}
