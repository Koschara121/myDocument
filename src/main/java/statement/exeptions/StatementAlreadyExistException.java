package statement.exeptions;

public class StatementAlreadyExistException extends Exception {
    public StatementAlreadyExistException(String message) {
        super(message);
    }
}
