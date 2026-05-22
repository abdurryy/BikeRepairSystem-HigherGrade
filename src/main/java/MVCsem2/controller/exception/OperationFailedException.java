package MVCsem2.controller.exception;

/**
 * Thrown when an operation in the controller fails. This exception wraps 
 * lower-level exceptions to maintain a clean abstraction for the view.
 */
public class OperationFailedException extends Exception {

    /**
     * Creates a new instance with a descriptive message and the original cause.
     *
     * @param message A message describing the failed operation for the user.
     * @param cause   The original exception that caused this failure (e.g., a database error).
     */
    public OperationFailedException(String message, Exception cause) {
        super(message, cause);
    }
}
