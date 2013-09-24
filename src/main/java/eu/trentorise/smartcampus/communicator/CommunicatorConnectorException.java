package eu.trentorise.smartcampus.communicator;

/**
 * Exception thrown by {@link CommunicatorConnector}
 * 
 */
public class CommunicatorConnectorException extends Exception {

	private static final long serialVersionUID = -6682965816616260202L;

	public CommunicatorConnectorException() {
		super();
	}

	public CommunicatorConnectorException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommunicatorConnectorException(String message) {
		super(message);
	}

	public CommunicatorConnectorException(Throwable cause) {
		super(cause);
	}

}
