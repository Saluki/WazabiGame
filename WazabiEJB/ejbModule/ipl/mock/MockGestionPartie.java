package ipl.mock;

import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class MockGestionPartie implements MockInterfaceGestionPartie {

	public MockGestionPartie() {
		// TODO Auto-generated constructor stub
	}
	public boolean verificationPseudo(String pseudo){
		if( pseudo.equals("charles")){
			return false;
		}
		return true;
		
	}
	public boolean inscription(String pseudo,String mdp){
		return true;
	}
	@Override
	public boolean verificationAuthentification(String pseudo, String mdp) {
		// TODO Auto-generated method stub
		if(pseudo != "Roger" && mdp != "federer")
			return false;
		return true;
	}
	
}
