package ovh.gorillahack.wazabi.exception;

public class XmlParsingException extends Exception {
	public XmlParsingException() {
		super();
	}

	public XmlParsingException(Exception e) {
		super(e);
	}

	public XmlParsingException(String message) {
		super(message);
	}
}
