package exception;

public class ParserFailureException extends RuntimeException {
    public ParserFailureException(String message) {
        super(message);
    }
}