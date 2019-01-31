package easyorderappdesktop.businessLogic;

/**
 * Custom Exception for business logic.
 *
 * @author Imanol
 */
public class BusinessLogicException extends Exception {

	/**
	 * Creates a new instance of <code>BusinessLogicException</code> without
	 * detail message.
	 */
	public BusinessLogicException() {
	}

	/**
	 * Constructs an instance of <code>BusinessLogicException</code> with the
	 * specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public BusinessLogicException(String msg) {
		super(msg);
	}
}
