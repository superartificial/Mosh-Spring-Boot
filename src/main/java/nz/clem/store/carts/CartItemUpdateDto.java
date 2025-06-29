package nz.clem.store.carts;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemUpdateDto {

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    @Max(value = 100, message = "Quantity must be less than 100")
    public Integer quantity;
//    public int quantity;

}
