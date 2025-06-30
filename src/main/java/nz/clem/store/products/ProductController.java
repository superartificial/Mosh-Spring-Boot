package nz.clem.store.products;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/*
Note: Needs to be refactored to use a Service layer
 */

@CrossOrigin(origins = "*")
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
        System.out.println("GET PRODUCTS: categoryId = " + categoryId );
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

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder
    ) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(category == null) {
            return ResponseEntity.badRequest().build();
        }
        var product = productMapper.toEntity(productDto);
        product.setCategory(category);
        product = productRespository.save(product);
        var uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        productDto.setId(product.getId());
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody ProductDto productDto
    ) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(category == null) {
            return ResponseEntity.badRequest().build();
        }
        var product = productRespository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        product.setCategory(category);
        productMapper.update(productDto, product);
        productRespository.save(product);
        productDto.setId(product.getId());
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        var product = productRespository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        productRespository.delete(product);
        return ResponseEntity.noContent().build();
    }

}
