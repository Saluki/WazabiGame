package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardConstraintViolatedException;

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
	public boolean utiliserCarte(Carte c) throws CardConstraintViolatedException {
		if(!validerCarte(c))
			return super.utiliserCarte(c);
		return super.utiliserCarte(c);
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Joueur j) throws CardConstraintViolatedException {
		if(!validerCarte(c))
			return super.utiliserCarte(c,j);
		 gp.donnerDes(j, j.getId_joueur());
		 return true;
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Sens sens) throws CardConstraintViolatedException {
		return super.utiliserCarte(c, sens);
	}
	
}
