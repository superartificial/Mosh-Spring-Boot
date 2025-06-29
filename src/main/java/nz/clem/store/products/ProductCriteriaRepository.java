package nz.clem.store.products;

import java.math.BigDecimal;
import java.util.List;

public interface ProductCriteriaRepository {

    List<Product> findProductsByCriteria(String name, BigDecimal minPrice, BigDecimal maxPrice);

}
