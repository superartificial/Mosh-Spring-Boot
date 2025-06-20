package nz.clem.store.mappers;

import nz.clem.store.dtos.CartDto;
import nz.clem.store.dtos.CartItemDto;
import nz.clem.store.dtos.ProductCartDto;
import nz.clem.store.entities.Cart;
import nz.clem.store.entities.CartItem;
import nz.clem.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java( cart.getTotalPrice() )")
    CartDto toDto(Cart cart);

    //    @Mapping(target = "totalPrice", expression = "java( cartItem.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(cartItem.getQuantity())) )")
    @Mapping(target = "totalPrice", expression = "java( cartItem.getTotalPrice() )") // can call methods on the cartItem object because it is a parameter of the method
    CartItemDto toDto(CartItem cartItem);


    ProductCartDto toDto(Product product);

}
