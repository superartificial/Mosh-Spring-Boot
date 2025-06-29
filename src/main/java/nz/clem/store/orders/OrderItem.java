package nz.clem.store.orders;

import jakarta.persistence.*;
import lombok.*;
import nz.clem.store.products.Product;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Order order, Product product, Integer quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
