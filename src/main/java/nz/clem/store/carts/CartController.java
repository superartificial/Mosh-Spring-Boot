package nz.clem.store.carts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nz.clem.store.common.ErrorDto;
import nz.clem.store.common.exceptions.CartNotFoundException;
import nz.clem.store.products.ProductNotFoundException;
import nz.clem.store.products.ProductNotInCartException;
import nz.clem.store.products.ProductRespository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/carts")
@AllArgsConstructor
@Tag(name = "Carts", description = "Operations for carts")
public class CartController {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRespository productRespository;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Add an item to a cart")
    public ResponseEntity<CartItemDto> addItemToCart(
            @Parameter(description = "The ID of the cart to add the item to")
            @PathVariable UUID cartId,
            @RequestBody CartItemRequestDto requestData
    ) {
        CartItemDto cartItemDto = cartService.addToCart(cartId, requestData.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public CartDto getCartById(@PathVariable UUID cartId) {
        return cartService.getCart(cartId);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    @Operation(summary = "Remove an item from a cart")
    public ResponseEntity<?> removeItemFromCart(@PathVariable UUID cartId, @PathVariable Long productId) {
        cartService.removeItem(cartId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId) {
        cartService.clearCartAndSave(cartId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId,
            @Valid @RequestBody CartItemUpdateDto requestData
    ) {
        CartItemDto cartItemDto = cartService.updateItem( cartId, productId, requestData.quantity);
        return ResponseEntity.ok(cartItemDto);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Product not found"));
    }

    @ExceptionHandler(ProductNotInCartException.class)
    public ResponseEntity<ErrorDto> handleProductNotInCartException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Product not in cart"));
    }

}
