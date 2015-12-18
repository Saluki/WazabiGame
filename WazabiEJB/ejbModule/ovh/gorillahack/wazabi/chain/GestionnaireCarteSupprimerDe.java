package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardConstraintViolatedException;

// implémentée
public class GestionnaireCarteSupprimerDe extends GestionnaireCarte {
	public GestionnaireCarteSupprimerDe(GestionnaireCarte next) {
		super(next);
	}

	@Override
	public boolean validerCarte(Carte c) {
		if (c.getCodeEffet() == 3)
			return true;
		return false;
	}

	// ici
	@Override
	public boolean utiliserCarte(Carte c) throws CardConstraintViolatedException {
		if (!validerCarte(c))
			return super.utiliserCarte(c);
		Joueur courant = gp.getJoueurCourant();
		for (int i = 0; i < 2; i++) {
			gp.supprimerDe(courant);
		}
		return true;
	}

	@Override
	public boolean utiliserCarte(Carte c, Joueur j) throws CardConstraintViolatedException {
		return super.utiliserCarte(c, j);
	}

	@Override
	public boolean utiliserCarte(Carte c, Sens sens) throws CardConstraintViolatedException {
		return super.utiliserCarte(c, sens);
	}

}
