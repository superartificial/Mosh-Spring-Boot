package nz.clem.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public void addCategory(Category category) {
        this.category = category;
        category.getProducts().add(this);
    }

    public void removeCategory(Category category) {
        this.category = category;
        category.getProducts().remove(this);
    }

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    // No need to create this because we'll never need to get all users who have this product in their wishlist
    //    @ManyToMany(mappedBy = "wishlist")
    //    private Set<User> users = new HashSet<>();

}