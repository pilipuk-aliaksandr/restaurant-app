package by.pilipuk.orders.environment.data.entityCreation;

import by.pilipuk.orders.business.repository.OrderItemRepository;
import by.pilipuk.orders.model.entity.Order;
import by.pilipuk.orders.model.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderItemsCreator {

    private final OrderItemRepository orderItemRepository;

    private OrderItem createOrderItem(Long id, Order order, String name) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        orderItem.setOrder(order);
        orderItem.setName(name);
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

    public OrderItem saveTestOrderItemToDB(OrderItem orderItem) {
        orderItem.setId(null);
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> saveTestOrderItemsToDB(Order order) {
        return List.of(
                saveTestOrderItemToDB(createOrderItem(1L, order, "Pizza")),
                saveTestOrderItemToDB(createOrderItem(2L, order, "Coca-Cola"))
        );
    }
}