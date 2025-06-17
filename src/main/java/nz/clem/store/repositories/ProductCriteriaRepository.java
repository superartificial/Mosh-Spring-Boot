package nz.clem.store.repositories;

import nz.clem.store.entities.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductCriteriaRepository {

    List<Product> findProductsByCriteria(String name, BigDecimal minPrice, BigDecimal maxPrice);

}
