package ovh.gorillahack.wazabi.exception;

public class CardConstraintViolatedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CardConstraintViolatedException() {
	}
	
	public CardConstraintViolatedException(Exception e) {
		super(e);
	}

	public CardConstraintViolatedException(String message) {
		super(message);
	}

	public CardConstraintViolatedException(Throwable cause) {
		super(cause);
	}

	public CardConstraintViolatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public CardConstraintViolatedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
