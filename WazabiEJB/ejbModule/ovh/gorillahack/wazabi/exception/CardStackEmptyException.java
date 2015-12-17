package ovh.gorillahack.wazabi.exception;

@SuppressWarnings("serial")
public class CardStackEmptyException extends Exception {

	public CardStackEmptyException() {
		// TODO Auto-generated constructor stub
	}
	
	public CardStackEmptyException(Exception e) {
		super(e);
	}

	public CardStackEmptyException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CardStackEmptyException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CardStackEmptyException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CardStackEmptyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
