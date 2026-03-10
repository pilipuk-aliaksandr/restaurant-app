package by.pilipuk.kitchen.environment.data.entityCreation;

import by.pilipuk.kitchen.business.repository.OrderItemRepository;
import by.pilipuk.kitchen.model.entity.Order;
import by.pilipuk.kitchen.model.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderItemCreator {

    private final OrderItemRepository orderItemRepository;

    private OrderItem createOrderItem(Long id, Order order, String name) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        orderItem.setOrder(order);
        orderItem.setName(name);
        orderItem.setCooked(false);
        orderItem.setActive(true);
        orderItem.setCreatedAt(LocalDateTime.now());

        return orderItem;
    }

    public List<OrderItem> createOrderItems(Order order) {
        return List.of(
                createOrderItem(1L, order, "Pizza"),
                createOrderItem(2L, order, "Coca-Cola")
        );
    }

    private OrderItem saveOrderItem(OrderItem orderItem) {
        orderItem.setId(null);
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> saveOrderItems(Order order) {
        return List.of(
                saveOrderItem(createOrderItem(1L, order, "Pizza")),
                saveOrderItem(createOrderItem(2L, order, "Coca-Cola"))
        );
    }
}