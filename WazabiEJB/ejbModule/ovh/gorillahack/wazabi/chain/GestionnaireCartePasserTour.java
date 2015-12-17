package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardConstraintViolatedException;

public class GestionnaireCartePasserTour extends GestionnaireCarte{
	public GestionnaireCartePasserTour(GestionnaireCarte next) {
		super(next);
	}
	
	@Override
	public boolean validerCarte(Carte c) {
		if(c.getCodeEffet()==9)
			return true;
		return false;
	}
	
	@Override
	public boolean utiliserCarte(Carte c) throws CardConstraintViolatedException {
		
		return super.utiliserCarte(c);
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Joueur j) throws CardConstraintViolatedException {
		if(!validerCarte(c))
			return super.utiliserCarte(c,j);
		return gp.passerTour(c,j);
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Sens sens) throws CardConstraintViolatedException {
		return super.utiliserCarte(c, sens);
	}

}
