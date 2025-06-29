package nz.clem.store.payments;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckoutRequest {

    @NotNull(message = "Cart ID is required")
    UUID cartId;

}
