package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;

public class GestionnaireCarteLaisser2CartesAdversaires extends GestionnaireCarte{
	public GestionnaireCarteLaisser2CartesAdversaires(GestionnaireCarte next){
		super(next);
	}
	
	@Override
	public boolean validerCarte(Carte c) {
		if(c.getCodeEffet()==8)
			return true;
		return false;
	}
	
	@Override
	public boolean utiliserCarte(Carte c) {
		if(!validerCarte(c))
			return super.utiliserCarte(c);
		//TODO ne laisser plus que 2 cartes aux autres joueurs
		return gp.laisserTousAdversairesAvecDeuxCartes(c);
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Joueur j) {
		if(!validerCarte(c))
			return super.utiliserCarte(c);
		return  super.utiliserCarte(c, j);
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Sens sens) {
		return super.utiliserCarte(c, sens);
	}

}
