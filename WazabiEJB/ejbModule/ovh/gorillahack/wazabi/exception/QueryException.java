package ovh.gorillahack.wazabi.exception;

@SuppressWarnings("serial")
public class QueryException extends Exception {

	public QueryException() {
		super();
	}
	
	public QueryException(Exception e) {
		super(e);
	}

	public QueryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public QueryException(String message, Throwable cause) {
		super(message, cause);
	}

	public QueryException(String message) {
		super(message);
	}

	public QueryException(Throwable cause) {
		super(cause);
	}

}
