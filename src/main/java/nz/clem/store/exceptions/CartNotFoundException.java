package nz.clem.store.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("Could not find cart");
    }
}
