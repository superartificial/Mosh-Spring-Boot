package nz.clem.store.orders;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order order);

    @Mapping(target = "product", source = "product")
    OrderItemDto toDto(OrderItem orderItem);

}
