package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardConstraintViolatedException;

public class GestionnaireCarteLaisserCarte extends GestionnaireCarte{
	public GestionnaireCarteLaisserCarte(GestionnaireCarte next){
		super(next);
	}
	
	@Override
	public boolean validerCarte(Carte c) {
		if(c.getCodeEffet()==6)
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
			return super.utiliserCarte(c, j);
		//TODO ne laisser qu'une carte à un adversaire
		gp.laisserAdversaireAvecDeuxCartes(c);
		return true;
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Sens sens) throws CardConstraintViolatedException {
		// TODO Auto-generated method stub
		return super.utiliserCarte(c, sens);
	}
}
