
package ovh.gorillahack.wazabi.chain;

import ovh.gorillahack.wazabi.domaine.Carte;
import ovh.gorillahack.wazabi.domaine.Joueur;
import ovh.gorillahack.wazabi.domaine.Partie.Sens;
import ovh.gorillahack.wazabi.exception.CardConstraintViolatedException;

public class GestionnaireCartePioche3 extends GestionnaireCarte {
	public GestionnaireCartePioche3(GestionnaireCarte next) {
		super(next);
	}

	@Override
	public boolean validerCarte(Carte c) {
		boolean res = c.getCodeEffet() == 7;
		System.out.println("Pioche 3 : " + res);
		return res;
	}

	@Override
	public boolean utiliserCarte(Carte c) throws CardConstraintViolatedException {
		if (!validerCarte(c)) {
			System.out.println("Pioche 3 passée.");
			return super.utiliserCarte(c);
		}
		for (int i = 0; i < 3; i++) {
			System.out.println("On pioche !");
			gp.piocherUneCarte(gp.getJoueurCourant());
		}
		gp.remettreCarte(gp.getJoueurCourant(), c);
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
