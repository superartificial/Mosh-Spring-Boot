package nz.clem.store.users;

public class ExistingUserException extends RuntimeException {
    public ExistingUserException() {
        super("User already exists");
    }
}
