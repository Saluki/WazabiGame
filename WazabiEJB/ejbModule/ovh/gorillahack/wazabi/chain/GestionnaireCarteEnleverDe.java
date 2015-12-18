package ovh.gorillahack.wazabi.chain;

import java.util.List;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.De;
import ovh.gorillahack.wazabi.domaine.Face.Valeur;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardConstraintViolatedException;

// implémentée
public class GestionnaireCarteEnleverDe extends GestionnaireCarte {

	public GestionnaireCarteEnleverDe(GestionnaireCarte next) {
		super(next);
	}

	@Override
	public boolean validerCarte(Carte c) {
		return c.getCodeEffet() == 1;
	}

	// ici
	@Override
	public boolean utiliserCarte(Carte c) throws CardConstraintViolatedException {
		if (!validerCarte(c))
			return super.utiliserCarte(c);
		Joueur courant = gp.getJoueurCourant();
		// on teste la contrainte : pas plus de 2 dés "DE"
		List<De> des = gp.voirDes(courant);
		int count = 0;
		for (De de : des) {
			if (de.getValeur() == Valeur.DE) {
				count++;
				if (count >= 2) {
					throw new CardConstraintViolatedException();
				}
			}
		}
		gp.supprimerDe(courant);
		return true;
	}

	@Override
	public boolean utiliserCarte(Carte c, Joueur j) throws CardConstraintViolatedException {
		return super.utiliserCarte(c, j);
	}

	@Override
	public boolean utiliserCarte(Carte c, Sens sens) throws CardConstraintViolatedException {
		// TODO Auto-generated method stub
		return super.utiliserCarte(c, sens);
	}

}
