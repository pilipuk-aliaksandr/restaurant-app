package by.pilipuk.kitchen.environment.data.entityCreation;

import by.pilipuk.kitchen.business.repository.OrderRepository;
import by.pilipuk.kitchen.model.entity.Order;
import by.pilipuk.kitchen.model.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OrderCreator {

    private final OrderRepository orderRepository;

    public Order createOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderId(1L);
        order.setStatus(Status.ACCEPTED);
        order.setActive(true);
        order.setCreatedAt(LocalDateTime.now());

        return order;
    }

    public Order saveOrder(Order order) {
        order.setId(null);
        return orderRepository.save(order);
    }
}