package ovh.gorillahack.wazabi.exception;

@SuppressWarnings("serial")
public class QueryException extends Exception {

	public QueryException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QueryException(Exception e) {
		super(e);
	}

	public QueryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public QueryException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public QueryException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public QueryException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
