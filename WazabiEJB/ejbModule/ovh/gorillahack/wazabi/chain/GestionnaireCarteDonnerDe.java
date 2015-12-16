package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;

public class GestionnaireCarteDonnerDe extends GestionnaireCarte{
	public GestionnaireCarteDonnerDe(GestionnaireCarte next){
		super(next);
	}
	@Override
	public boolean validerCarte(Carte c) {
		if(c.getCodeEffet()==4)
			return true;
		return false;
	}
	
	@Override
	public boolean utiliserCarte(Carte c) {
		if(!validerCarte(c))
			return super.utiliserCarte(c);
		//TODO donner un de à un joueur
		return super.utiliserCarte(c);
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Joueur j) {
		return super.utiliserCarte(c, j);
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Sens sens) {
		return super.utiliserCarte(c, sens);
	}
	
}
