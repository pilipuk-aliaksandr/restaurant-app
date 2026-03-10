package by.pilipuk.orders.environment.data.eventCreation;

import by.pilipuk.common.model.dto.OrderCreatedEvent;
import by.pilipuk.common.model.dto.OrderReadyEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventCreator {

    public OrderCreatedEvent createOrderCreatedEvent() {
        var orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setOrderId(1L);
        orderCreatedEvent.setTableNumber(1);
        orderCreatedEvent.setItems(List.of("Pizza", "Coca-Cola"));

        return orderCreatedEvent;
    }

    public OrderReadyEvent createOrderReadyEvent() {
        var orderReadyEvent = new OrderReadyEvent();
        orderReadyEvent.setOrderId(1L);
        orderReadyEvent.setItems(List.of("Pizza", "Coca-Cola"));

        return orderReadyEvent;
    }
}
