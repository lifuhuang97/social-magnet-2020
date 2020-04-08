package project.exception;

public class KillThreadException extends RuntimeException {
    /**
     * Constructs KillThreadException Object 
     * @param msg error message
     */
    public KillThreadException(String msg) {
        super(msg);
    }
}