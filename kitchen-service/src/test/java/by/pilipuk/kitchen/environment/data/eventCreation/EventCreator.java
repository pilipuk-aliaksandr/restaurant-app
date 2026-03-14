package by.pilipuk.kitchen.environment.data.eventCreation;

import by.pilipuk.commonKafka.model.dto.OrderCreatedEvent;
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
}