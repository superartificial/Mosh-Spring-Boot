package nz.clem.store.carts;

import nz.clem.store.products.Product;
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
