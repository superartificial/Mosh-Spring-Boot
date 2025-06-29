package nz.clem.store.orders;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {

    private Integer quantity;
    private BigDecimal totalPrice;
    private OrderProductDto product;

}
