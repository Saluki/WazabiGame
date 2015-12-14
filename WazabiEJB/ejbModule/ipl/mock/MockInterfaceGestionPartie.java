package ipl.mock;

import java.util.Stack;

import javax.ejb.Remote;

@Remote
public interface MockInterfaceGestionPartie {

	public boolean verificationPseudo(String pseudo);
	public boolean inscription(String pseudo,String mdp);
	public boolean verificationAuthentification(String pseudo,String mdp);
	public Stack pileInformation();
}
