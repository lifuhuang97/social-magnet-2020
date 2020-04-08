package project.exception;

/**
 * This is exception folder handling all the errors with regards to registering 
 */

public class RegisterException extends RuntimeException {
    /**
     * Constructs RegisterException
     * @param msg error message
     */
    public RegisterException(String msg) {
        super(msg);
    }
}