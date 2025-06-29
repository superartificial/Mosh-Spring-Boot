package nz.clem.store.carts;

import jakarta.persistence.*;
import lombok.*;
import nz.clem.store.products.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)", name = "id")
    private UUID id;

    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
//    private List<CartItem> items = new ArrayList<>();
    private Set<CartItem> items = new LinkedHashSet<>(); // use Set because we don't need duplicates'


    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    public void removeItem(Long productId) {
        var item = getItem(productId);
        if (item != null) {
            items.remove(item);
            item.setCart(null);
        }
    }

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public CartItem getItem(Long productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public CartItem addItem(Product product) {
        var cartItem = getItem(product.getId());
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new CartItem();
            cartItem.setQuantity(1);
            cartItem.setProduct(product);
            addItem(cartItem);
        }
        return cartItem;
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

//    @PrePersist
//    protected void onCreate() {
//        dateCreated = LocalDateTime.now();
//    }
}