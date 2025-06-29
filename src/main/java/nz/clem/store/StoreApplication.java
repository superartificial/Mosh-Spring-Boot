package nz.clem.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(StoreApplication.class, args);

//        var service = context.getBean(UserService.class);
//        service.deleteRelated();

//        var service = context.getBean(ProductService.class);
//        service.fetchPaginatedProducts(0, 3);

    }

}
