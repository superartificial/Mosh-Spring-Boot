package nz.clem.store.dtos;

import lombok.*;

import java.math.BigDecimal;

@Data
public class ProductDto {

    Long id;
    String name;
    String description;
    BigDecimal price;
    Byte categoryId;

}
