package ovh.gorillahack.wazabi.exception;

public class PlayerNotFoundException extends Exception {
	public PlayerNotFoundException(){
		super();
	}
	
	public PlayerNotFoundException(String message){
		super(message);
	}
}
