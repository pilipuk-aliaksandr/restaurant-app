package by.pilipuk.orders.environment.data.entityCreation;

import by.pilipuk.orders.business.repository.OrderRepository;
import by.pilipuk.orders.model.entity.Order;
import by.pilipuk.orders.model.entity.Status;
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
        order.setTableNumber(1);
        order.setStatus(Status.CREATED);
        order.setActive(true);
        order.setCreatedAt(LocalDateTime.now());

        return order;
    }

    public Order saveTestOrderToDB(Order order) {
        order.setId(null);
        return orderRepository.save(order);
    }
}