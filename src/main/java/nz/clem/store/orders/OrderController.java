package nz.clem.store.orders;

import lombok.AllArgsConstructor;
import nz.clem.store.common.exceptions.OrderNotFoundException;
import nz.clem.store.common.ErrorDto;
import nz.clem.store.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final AuthService authService;
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getOrders(@RequestHeader("Authorization") String authHeader) {
        return orderService.ordersForUser();
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(Exception ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Void> handleOrderNotFoundException() {
        return ResponseEntity.notFound().build();
    }

}
