package nz.clem.store.products;

import lombok.AllArgsConstructor;
import nz.clem.store.users.UserRepository;
import nz.clem.store.common.ProductSpec;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class DemoProductService {

    private final UserRepository userRepository;
    private final ProductRespository productRepository;
    private final CategoryRepository categoryRepository;
//    private final ProductCriteriaRepositoryImpl productCriteriaRepository;


    public void createProductWithNewCategory() {
        var category = new Category("New Category");
//        category = categoryRepository.save(category);

        var product = Product.builder()
                .name("Newer Product")
                .description("Pierre's new product")
                .price(BigDecimal.valueOf(200))
                .build();

        product.addCategory(category);
        productRepository.save(product);
    }

    @Transactional
    public void createProductForExistingCategory() {

        var category = categoryRepository.findById((byte)1).orElseThrow();
        var product = Product.builder()
                .name( "Product 2")
                .description(" Product Description")
                .price(BigDecimal.valueOf(500))
                .category(category)
                .build();
        productRepository.save(product);

    }

    @Transactional
    public void deleteProduct() {
        productRepository.deleteById(5L);
    }

    @Transactional
    public void addProductsToWishlist() {
        var user = userRepository.findById(2L).orElseThrow();
        productRepository.findAll().forEach(user::addToWishlist);
        userRepository.save(user);
    }

    @Transactional
    public void updatePriceByCategory() {
        productRepository.updatePriceByCategory(BigDecimal.valueOf(100), (byte)1);
    }

    public void fetchProductsByCategory() {
//        productRepository.findByCategory(new Category((byte)4))
//                .forEach(System.out::println);

    }

    @Transactional
    public void fetchProductsByPrice() {
        productRepository.findProducts(BigDecimal.valueOf(200), BigDecimal.valueOf(350))
        .forEach(System.out::println);
    }

    @Transactional
    public void fetchProductsByExample() {
        var product = new Product();
        product.setName("Newer Product");

        var matcher = ExampleMatcher.matching()
                .withIncludeNullValues()
                .withIgnorePaths("id","description")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        var example = Example.of(product, matcher);
        var products = productRepository.findAll(example);
        products.forEach(System.out::println);
    }

    public void fetchProductsByCriteria() {
        var products = productRepository.findProductsByCriteria(null, BigDecimal.valueOf(100), BigDecimal.valueOf(300));
        products.forEach(System.out::println);
    }

    public void fetchProductsBySpecifications(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        Specification<Product> spec = Specification.where(null);
        if(name != null) {
            spec = spec.and(ProductSpec.hasName(name));
        }
        if(minPrice != null) {
            spec = spec.and(ProductSpec.hasPriceGreaterThanOrEqualTo(minPrice));
        }
        if(maxPrice != null) {
            spec = spec.and(ProductSpec.hasPriceLessThanOrEqualTo(maxPrice));
        }
        productRepository.findAll(spec).forEach(System.out::println);
    }

    public void fetchSortedProducts() {
        Sort.by("name").and(
                Sort.by("price").descending()
        );
        productRepository.findAll(Sort.by("name")).forEach(System.out::println);

    }

    public void fetchPaginatedProducts(int pageNumer, int size) {
        PageRequest pageRequest = PageRequest.of(pageNumer, size);
        Page<Product> page =  productRepository.findAll(pageRequest);
        var products = page.getContent();
        products.forEach(System.out::println);

        var totalPages = page.getTotalPages();
        var totalElements = page.getTotalElements();
        System.out.println("Total pages: " + totalPages);
        System.out.println("Total elements: " + totalElements);
    }

}
