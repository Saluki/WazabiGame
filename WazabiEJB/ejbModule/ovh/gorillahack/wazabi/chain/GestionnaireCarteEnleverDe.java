package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;

public class GestionnaireCarteEnleverDe extends GestionnaireCarte{

	public GestionnaireCarteEnleverDe(GestionnaireCarte next) {
		super(next);
	}
	
	@Override
	public boolean validerCarte(Carte c) {
		return c.getCodeEffet()==1;
	}
	
	@Override
	public boolean utiliserCarte(Carte c) {
		if(!validerCarte(c))
			return super.utiliserCarte(c);
		//TODO enlever le de au joueur
		return true;
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Joueur j) {
		return super.utiliserCarte(c, j);
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Sens sens) {
		// TODO Auto-generated method stub
		return super.utiliserCarte(c, sens);
	}

}
