package ovh.gorillahack.wazabi.exception;

@SuppressWarnings("serial")
public class CardStackEmptyException extends Exception {

	public CardStackEmptyException() {
	}
	
	public CardStackEmptyException(Exception e) {
		super(e);
	}

	public CardStackEmptyException(String message) {
		super(message);
	}

	public CardStackEmptyException(Throwable cause) {
		super(cause);
	}

	public CardStackEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public CardStackEmptyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
