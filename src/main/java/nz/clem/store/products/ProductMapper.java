package nz.clem.store.products;

import nz.clem.store.orders.OrderProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);

    @Mapping(target = "category.id", source = "categoryId")
    Product toEntity(ProductDto productDto);

    OrderProductDto toOrderProductDto(Product product);

    @Mapping( target = "id", ignore = true)
    void update(ProductDto productDto, @MappingTarget Product product);

}
