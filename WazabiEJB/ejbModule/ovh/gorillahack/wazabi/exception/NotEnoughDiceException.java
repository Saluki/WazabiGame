package ovh.gorillahack.wazabi.exception;

public class NotEnoughDiceException extends Exception {
	public NotEnoughDiceException(){
		super();
	}
	
	public NotEnoughDiceException(String message){
		super(message);
	}
}
