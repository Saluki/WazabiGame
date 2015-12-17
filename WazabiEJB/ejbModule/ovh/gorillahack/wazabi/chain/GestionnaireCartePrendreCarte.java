package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;

public class GestionnaireCartePrendreCarte extends GestionnaireCarte{
	public GestionnaireCartePrendreCarte(GestionnaireCarte next){
		super(next);
	}
	
	@Override
	public boolean validerCarte(Carte c) {
		if(c.getCodeEffet()==5)
			return true;
		return false;
	}

	@Override
	public boolean utiliserCarte(Carte c) {
		 gp.piocherUneCarteChezUnJoueur(c);
		 return true;
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Joueur j) {
		if(!validerCarte(c))
			return super.utiliserCarte(c,j);
		//TODO donner une carte au joueur courant
		return super.utiliserCarte(c, j);
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Sens sens) {
		return super.utiliserCarte(c, sens);
	}
}
