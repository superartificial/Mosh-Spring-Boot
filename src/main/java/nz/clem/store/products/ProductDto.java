package nz.clem.store.products;

import lombok.*;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Byte categoryId;

}
