package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;

public class GestionnaireCarteTournerDe extends GestionnaireCarte{

	public GestionnaireCarteTournerDe(GestionnaireCarte next) {
		super(next);
	}
	
	@Override
	public boolean validerCarte(Carte c) {
		return c.getCodeEffet()==2;
	}
	
	@Override
	public boolean utiliserCarte(Carte c) {
		return super.utiliserCarte(c);
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Joueur j) {
		return super.utiliserCarte(c, j);
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Sens sens) {
		if(!validerCarte(c))
			return super.utiliserCarte(c,sens);
		// TODO faire tourner les des dans le sens donne
		return super.utiliserCarte(c, sens);
	}
	
}
