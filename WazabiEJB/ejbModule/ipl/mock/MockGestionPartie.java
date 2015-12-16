package ipl.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Face;
import ovh.gorillahack.wazabi.domaine.Joueur;

@Singleton
@Startup
public class MockGestionPartie implements MockInterfaceGestionPartie {
	private EtatPartie etatPartie = EtatPartie.PAS_COMMENCE;
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
		mapRetour.put("name", "john Doe");
		mapRetour.put("play", "true");
		return mapRetour;
	}

	public Vector<Map<String, String>> carteInformation() {
		// TODO Auto-generated method stub
		// le vecteur a renvoy�
		Vector vecRetour = new Vector<Map<String, String>>();

		// on ajoute un Map permettant de retenir les clefs valeurs des cartes
		vecRetour.addElement(new HashMap<String, String>());

		// on va regarder a la premiere carte
		HashMap<String, String> map = (HashMap<String, String>) vecRetour.get(vecRetour.size() - 1);
		// 1iere carte
		map.put("name", "Card A");
		map.put("description", "Description of A");
		map.put("image", "/image/pic1.png");
		map.put("effect", "2");
		map.put("cost", "3");
		map.put("input", "true");

		// ajout d'une map pour la carte 2
		vecRetour.addElement(new HashMap<String, String>());
		// 2ieme carte
		map = (HashMap<String, String>) vecRetour.get(vecRetour.size() - 1);

		map.put("name", "Card B");
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
			vector.addElement("WAZABI");
			vector.addElement("PIOCHE");
			vector.addElement("DE");
			return vector;
		case 1:
			vector.addElement("PIOCHE");
			vector.addElement("DE");
			return vector;
		case 2:
			vector.addElement("WAZABI");
			vector.addElement("WAZABI");
			return vector;

		}
		return vector;

	}

	@SuppressWarnings("unchecked")
	public Vector<Map<String, Vector<String>>> challengersInformations() {
		// TODO Auto-generated method stub
		// le vecteur a renvoy�
		Vector<Map<String, Vector<String>>> vecRetour = new Vector<Map<String, Vector<String>>>();

		// on ajoute un Map permettant de retenir les clefs valeurs des cartes
		vecRetour.addElement(new HashMap<String, Vector<String>>());

		// on va regarder a la premiere carte
		HashMap<String, Vector<String>> map = (HashMap<String, Vector<String>>) vecRetour.get(vecRetour.size() - 1);

		// 1ier challenger
		Vector<String> nameChallenger = new Vector<String>();
		nameChallenger.add("Jack Forge");
		map.put("name", nameChallenger);
		map.put("dices", desInformation(1));

		// ajout d'une map pour le challenger 2
		vecRetour.addElement(new HashMap<String, Vector<String>>());

		// 2ieme challenger
		HashMap<String, Vector<String>> map2 = (HashMap<String, Vector<String>>) vecRetour.get(vecRetour.size() - 1);

		Vector nameChallenger2 = new Vector<String>();
		nameChallenger2.add("Alexandre Neil");
		map2.put("name", nameChallenger2);
		map2.put("dices", desInformation(2));

		return vecRetour;
	}

	@Override
	public Stack pileInformation() {
		Stack pile = new Stack<Object>();
		// Map<String, String>
		pile.push(joueurInformation());
		// Vector<Map<String, String>>
		pile.push(carteInformation());
		// Vector<String>
		pile.push(desInformation(0));
		// Vector<Map<String, Vector<String>>>
		pile.push(challengersInformations());
		return pile;
	}

	public EtatPartie etatPartie() {
		// TODO Auto-generated method stub
		return etatPartie;
	}

	@Override
	public boolean createPartie(String partie) {
		// TODO Auto-generated method stub
		if(etatPartie.equals(EtatPartie.PAS_COMMENCE)){
			etatPartie = EtatPartie.EN_ATTENTE;
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<De> lancerDes(Joueur joueur) {
		// TODO Auto-generated method stub
		List<De> listeRetour = new ArrayList<De>();
		listeRetour.add(new De(Face.Valeur.DE));
		listeRetour.add(new De(Face.Valeur.DE));
		listeRetour.add(new De(Face.Valeur.WAZABI));
		return listeRetour;
	}

	@Override
	public Map<String, String> carteJouer(int carte) {
		// TODO Auto-generated method stub
		// on va regarder a la premiere carte
			HashMap<String, String> map = new HashMap<String, String>();
				// 1iere carte
				map.put("name", "Card A");
				map.put("description", "Description of A");
				map.put("image", "/image/pic1.png");
				map.put("effect", "2");
				map.put("cost", "3");
				map.put("input", "true");
			return map;
	}

}
