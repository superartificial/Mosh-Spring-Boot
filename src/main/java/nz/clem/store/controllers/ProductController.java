package nz.clem.store.controllers;

import lombok.AllArgsConstructor;
import nz.clem.store.dtos.ProductDto;
import nz.clem.store.entities.Product;
import nz.clem.store.mappers.ProductMapper;
import nz.clem.store.repositories.CategoryRepository;
import nz.clem.store.repositories.ProductRespository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRespository productRespository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @RequestMapping("")
    public List<ProductDto> getProducts(
            @RequestParam(name = "categoryId", required = false) Byte categoryId
    ) {
        List<Product> products;
        if(categoryId != null) {
            products = productRespository.findByCategoryId(categoryId);
        } else {
            products = productRespository.findAllWithCategory();
        }
        return products.stream().map(productMapper::toDto).toList();
    }

    @RequestMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        var product = productRespository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDto(product));
    }

}
