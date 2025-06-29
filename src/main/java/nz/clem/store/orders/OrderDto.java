package nz.clem.store.orders;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDto {

    private Long id;
    private String status;
    private LocalDateTime createdAt;
    private BigDecimal totalPrice;
    private Set<OrderItemDto> items = new HashSet<>();

}
