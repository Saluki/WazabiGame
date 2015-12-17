package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardConstraintViolatedException;

//implémentée
public class GestionnaireCarteTournerDe extends GestionnaireCarte {

	public GestionnaireCarteTournerDe(GestionnaireCarte next) {
		super(next);
	}

	@Override
	public boolean validerCarte(Carte c) {
		return c.getCodeEffet() == 2;
	}

	@Override
	public boolean utiliserCarte(Carte c) throws CardConstraintViolatedException {
		return super.utiliserCarte(c);
	}

	@Override
	public boolean utiliserCarte(Carte c, Joueur j) throws CardConstraintViolatedException {
		return super.utiliserCarte(c, j);
	}

	// ici
	@Override
	public boolean utiliserCarte(Carte c, Sens sens) throws CardConstraintViolatedException {
		if (!validerCarte(c))
			return super.utiliserCarte(c, sens);
		
		return true;
	}

}
