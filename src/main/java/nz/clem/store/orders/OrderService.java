package nz.clem.store.orders;

import lombok.AllArgsConstructor;
import nz.clem.store.auth.AuthService;
import nz.clem.store.exceptions.OrderNotFoundException;
import nz.clem.store.repositories.OrderRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AuthService authService;

    public List<OrderDto> ordersForUser() {
        var user = authService.getCurrentUser();
        return orderRepository.getOrdersForCustomer(user).stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long orderId) {
        var order = orderRepository
                .getOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);
        var user = authService.getCurrentUser();
        if(!order.isPlacedBy(user)) {
            throw new AccessDeniedException("You don't have permission to access this order");
        }
        return orderMapper.toDto(order);
    }

}
