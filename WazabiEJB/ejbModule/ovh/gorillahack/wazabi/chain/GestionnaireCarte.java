package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;

public abstract class GestionnaireCarte {
	private GestionnaireCarte next;
	
	public GestionnaireCarte(GestionnaireCarte next){
		this.next = next;
	}
	
	public abstract boolean validerCarte(Carte c);
	
	public boolean utiliserCarte(Carte c){
		if(this.next!=null)
			return next.utiliserCarte(c);
		return false;
	}
}
