package nz.clem.store.carts;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCartDto {

    private Long id;
    private String name;
    private BigDecimal price;

}
