package nz.clem.store.carts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequestDto {

    @NotNull
    private Long productId;

}
