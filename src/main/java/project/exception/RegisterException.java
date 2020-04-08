package project.exception;

public class RegisterException extends RuntimeException {
    /**
     * Constructs RegisterException
     * @param msg error message
     */
    public RegisterException(String msg) {
        super(msg);
    }
}