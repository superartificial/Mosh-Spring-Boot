package nz.clem.store.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {

    private ProductCartDto product;
    private Integer quantity;
    private BigDecimal totalPrice;

}
