package project.exception;

/**
 * This is the exception file that handles any errors regarding killing threads/posts
 */

public class KillThreadException extends RuntimeException {
    /**
     * Constructs KillThreadException Object 
     * @param msg error message
     */
    public KillThreadException(String msg) {
        super(msg);
    }
}