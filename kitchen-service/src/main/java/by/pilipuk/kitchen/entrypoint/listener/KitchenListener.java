package by.pilipuk.kitchen.entrypoint.listener;

import by.pilipuk.common.model.dto.OrderCreatedEvent;
import by.pilipuk.kitchen.business.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KitchenListener {

    private final OrderService orderService;

    @KafkaListener(topics = "orders", groupId = "kitchen-group")
    public void listen(OrderCreatedEvent event) {
        orderService.acceptNewOrdersFromKafka(event);
    }
}