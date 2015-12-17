package ovh.gorillahack.wazabi.exception;

@SuppressWarnings("serial")
public class NoCurrentGameException extends Exception {
	public NoCurrentGameException(){
		super();
	}
	
	public NoCurrentGameException(String message){
		super(message);
	}
}
