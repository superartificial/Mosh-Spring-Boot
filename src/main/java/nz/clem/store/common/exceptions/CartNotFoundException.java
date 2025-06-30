package nz.clem.store.common.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("Could not find cart");
    }
}
