package ovh.gorillahack.wazabi.exception;

@SuppressWarnings("serial")
public class NotEnoughDiceException extends Exception {
	public NotEnoughDiceException(){
		super();
	}
	
	public NotEnoughDiceException(String message){
		super(message);
	}
}
