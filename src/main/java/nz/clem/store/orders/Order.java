package nz.clem.store.orders;

import jakarta.persistence.*;
import lombok.*;
import nz.clem.store.carts.Cart;
import nz.clem.store.payments.PaymentStatus;
import nz.clem.store.users.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "orders")
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "total_price")
    BigDecimal totalPrice;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    public void addItem(OrderItem orderItem) {
        items.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order fromCart(Cart cart, User customer) {
        BigDecimal total = cart.getItems().stream().reduce(BigDecimal.ZERO, (acc, item) -> acc.add(item.getTotalPrice()), BigDecimal::add);
        var order = Order.builder()
                .customer(customer)
                .status(PaymentStatus.PENDING)
                .totalPrice(total)
                .build();

        cart.getItems().forEach(item -> {
            var orderItem = new OrderItem(order, item.getProduct(),item.getQuantity());
            order.items.add(orderItem);
        });
        return order;
    }

    public boolean isPlacedBy(User customer) {
        return this.customer.equals(customer);
    }

}
