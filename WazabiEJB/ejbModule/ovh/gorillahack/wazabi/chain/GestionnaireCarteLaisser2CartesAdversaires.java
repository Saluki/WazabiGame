package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardConstraintViolatedException;

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
	public boolean utiliserCarte(Carte c) throws CardConstraintViolatedException {
		if(!validerCarte(c))
			return super.utiliserCarte(c);
		//TODO ne laisser plus que 2 cartes aux autres joueurs
		gp.laisserTousAdversairesAvecDeuxCartes(c);
		gp.remettreCarte(gp.getJoueurCourant(), c);
		return true;
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Joueur j) throws CardConstraintViolatedException {
		if(!validerCarte(c))
			return super.utiliserCarte(c,j);
		return  super.utiliserCarte(c, j);
	}
	
	@Override
	public boolean utiliserCarte(Carte c, Sens sens) throws CardConstraintViolatedException {
		return super.utiliserCarte(c, sens);
	}

}
