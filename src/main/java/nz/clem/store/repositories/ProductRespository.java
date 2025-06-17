package nz.clem.store.repositories;

import nz.clem.store.dtos.ProductSummary;
import nz.clem.store.entities.Category;
import nz.clem.store.entities.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface ProductRespository extends JpaRepository<Product, Long>, ProductCriteriaRepository, JpaSpecificationExecutor<Product> {

    @EntityGraph(attributePaths = "category")
    List<Product> findByCategoryId(Byte categoryId);

    @EntityGraph(attributePaths = "category")
    @Query("select p from Product p")
    List<Product> findAllWithCategory();












    /* DEMO */

    // Generally better to use interfaces for DTOs, only use classes when needing to add logic
    @Query("select new nz.clem.store.dtos.ProductSummaryDTO(p.id, p.name) from Product p where p.category = :category")
    List<ProductSummary> findByCategory(@Param("category") Category category);

    List<Product> findByName(String name);

//    @Query("select p from Product p left join p.category c where p.price between :min and :max order by p.name")
    @Procedure("pridProductsByPrice")
    List<Product> findProducts(BigDecimal min, BigDecimal max);

    @Query("select count(p) from Product p where p.price between :min and :max")
    long countProducts(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    @Modifying
    @Query("update Product p set p.price = :newPrice where p.category.id = :categoryId")
    void updatePriceByCategory(@Param("newPrice") BigDecimal newPrice,@Param("categoryId") Byte categoryId);

    Collection<Object> findAllByCategory(Category category);
}
