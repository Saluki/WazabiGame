package ipl.mock;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

import javax.ejb.Singleton;
import javax.ejb.Startup;



import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.usecases.GestionPartie;
import sun.swing.FilePane;

@Singleton
@Startup
public class MockGestionPartie implements MockInterfaceGestionPartie {

	public MockGestionPartie() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean verificationPseudo(String pseudo) {
		if (pseudo.equals("charles"))
			return false;
		return true;

	}
	@Override
	public boolean inscription(String pseudo, String mdp) {
		return true;
	}

	@Override
	public boolean verificationAuthentification(String pseudo, String mdp) {
		// TODO Auto-generated method stub
		if (pseudo.equals("azerty") && mdp.equals("azerty"))
			return true;
		return false;
	}

	
	public Map<String, String> joueurInformation() {
		// TODO Auto-generated method stub
		Map<String, String> mapRetour = new HashMap<String, String>();
		mapRetour.put("playerName", "john Doe");
		mapRetour.put("play", "true");
		return mapRetour;
	}

	public Vector<Map<String, String>> carteInformation() {
		// TODO Auto-generated method stub
		// le vecteur a renvoyé
		Vector vecRetour = new Vector<Map<String, String>>();

		// on ajoute un Map permettant de retenir les clefs valeurs des cartes
		vecRetour.addElement(new HashMap<String, String>());

		// on va regarder a la premiere carte
		HashMap<String, String> map = (HashMap<String, String>) vecRetour.get(vecRetour.size());
		// 1iere carte
		map.put("cardsName", "Card A");
		map.put("description", "Description of A");
		map.put("image", "/image/pic1.png");
		map.put("effect", "2");
		map.put("cost", "3");
		map.put("input", "true");

		// ajout d'une map pour la carte 2
		vecRetour.addElement(new HashMap<String, String>());
		// 2ieme carte
		map = (HashMap<String, String>) vecRetour.get(vecRetour.size());

		map.put("cardsName", "Card B");
		map.put("description", "Description of B");
		map.put("image", "/image/pic2.png");
		map.put("effect", "1");
		map.put("cost", "2");
		map.put("input", "false");

		return vecRetour;
	}

	public Vector<String> desInformation(int joueur) {
		Vector<String> vector = new Vector<String>();
		switch (joueur) {
		case 0:
			vector.addElement("A");
			vector.addElement("B");
			vector.addElement("C");
			return vector;
		case 1:
			vector.addElement("D");
			vector.addElement("E");
			return vector;
		case 2:
			vector.addElement("F");
			vector.addElement("G");
			return vector;
			
		}
		return vector;

	}

	@SuppressWarnings("unchecked")
	public Vector<Map<String, Vector<String>>> challengersInformations() {
		// TODO Auto-generated method stub
		// le vecteur a renvoyé
		Vector<Map<String, Vector<String>>> vecRetour = new Vector<Map<String, Vector<String>>>();

		// on ajoute un Map permettant de retenir les clefs valeurs des cartes
		vecRetour.addElement(new HashMap<String, Vector<String>>());

		// on va regarder a la premiere carte
		HashMap<String, Vector<String>> map = (HashMap<String, Vector<String>>) vecRetour.get(vecRetour.size());
		
		// 1ier challenger
		Vector<String> nameChallenger = new Vector<String>();
		nameChallenger.add("Jack Forge");
		map.put("name", nameChallenger);
		map.put("dices", desInformation(1));

		// ajout d'une map pour le challenger 2
		vecRetour.addElement(new HashMap<String, Vector<String>>());
		
		// 2ieme challenger
		map = (HashMap<String,Vector<String>>) vecRetour.get(vecRetour.size());
		
		Vector nameChallenger2 = new Vector<String>();
		nameChallenger2.add("Alexandre Neil");
		map.put("name",nameChallenger2);
		map.put("dices", desInformation(2));

		return vecRetour;
	}
	@Override
	public Stack pileInformation(){
		Stack pile = new Stack<Object>();
		//Map<String, String>
		pile.push(joueurInformation());
		//Vector<Map<String, String>>
		pile.push(carteInformation());
		//Vector<String>
		pile.push(desInformation(0));
		//Vector<Map<String, Vector<String>>>
		pile.push(challengersInformations());
		return pile;
	}

}
