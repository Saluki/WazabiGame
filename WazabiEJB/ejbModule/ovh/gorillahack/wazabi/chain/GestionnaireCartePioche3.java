
package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardConstraintViolatedException;
import ovh.gorillahack.wazabi.exception.PlayerNotFoundException;

public class GestionnaireCartePioche3 extends GestionnaireCarte{
	public GestionnaireCartePioche3(GestionnaireCarte next) {
		super(next);
	}
	
	@Override
	public boolean validerCarte(Carte c) {
		return c.getCodeEffet()==7;
	}
	
	@Override
	public boolean utiliserCarte(Carte c) throws CardConstraintViolatedException {
		if(!validerCarte(c))
			return super.utiliserCarte(c);
		for(int i = 0; i<3;i++){
			gp.piocherUneCarte(gp.getJoueurCourant());
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
