package edu.escuelaing.arep.taller6.consult;

public class ApplicationException extends Exception{
	
	/** Property serial. */
	private static final long serialVersionUID = 1L;
	
	/** Property ERROR_DB */
	public static final String ERROR_DB = "A problem occurred on the DB connection.";
	
	/** Property ERROR_NAME */
	public static final String ERROR_NAME = "Please insert the parameter name on url /add?data={name}";
	
	/** Property INSERT_SUCCESSFUL */
	public static final String INSERT_SUCCESSFUL = "Your insert was completed";

	public ApplicationException(String message) {
		super(message);
	}
}
