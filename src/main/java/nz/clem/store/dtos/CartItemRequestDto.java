package nz.clem.store.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemRequestDto {

    @NotNull
    private Long productId;

}
