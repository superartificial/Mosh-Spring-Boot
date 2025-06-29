package nz.clem.store.carts;

import lombok.AllArgsConstructor;
import nz.clem.store.exceptions.CartNotFoundException;
import nz.clem.store.products.ProductNotFoundException;
import nz.clem.store.products.ProductNotInCartException;
import nz.clem.store.products.ProductRespository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRespository productRespository;

    public CartDto createCart() {
        var newCart = cartRepository.save(new Cart());
        return cartMapper.toDto(newCart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        var product = productRespository.findById(productId).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }
        CartItem cartItem = cart.addItem(product);
        cartRepository.save(cart); // Save the cart, which cascades the save to CartItem
        return cartMapper.toDto(cartItem);
    }

    public CartItemDto updateItem(UUID cartId, Long productId, Integer quantity) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        var cartItem = cart.getItem(productId);
        if (cartItem == null) {
            throw new ProductNotInCartException();
        }
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public void removeItem(UUID cartId, Long productId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();
        var cartItem = cart.getItem(productId);
        if (cartItem == null) throw new ProductNotInCartException();
        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();
        cart.clear();
    }

    public void clearCartAndSave(UUID cartId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null) throw new CartNotFoundException();
        cart.clear();
        cartRepository.save(cart);
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();
        return cartMapper.toDto(cart);
    }
}
