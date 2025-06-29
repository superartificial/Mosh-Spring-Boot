package nz.clem.store.users;

public class IncorrectPreviousPasswordException extends RuntimeException {
    public IncorrectPreviousPasswordException() {
        super("Incorrect previous password");
    }
}
