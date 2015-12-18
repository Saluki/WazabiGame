package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardConstraintViolatedException;
import ovh.gorillahack.wazabi.exception.NoCurrentGameException;

public class GestionnaireCarteChangerSens extends GestionnaireCarte{
	public GestionnaireCarteChangerSens(GestionnaireCarte next) {
		super(next);
	}
	
	@Override
	public boolean validerCarte(Carte c) {
		if(c.getCodeEffet()==10)
			return true;
		return false;
	}
	
	@Override
	public boolean utiliserCarte(Carte c) throws CardConstraintViolatedException {
		if(!validerCarte(c))
			return super.utiliserCarte(c);
		try {
			gp.changementDeSens();
			gp.remettreCarte(gp.getJoueurCourant(), c);
		} catch (NoCurrentGameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
