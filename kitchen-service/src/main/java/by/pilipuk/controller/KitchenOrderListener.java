package by.pilipuk.controller;

import by.pilipuk.dto.OrderCreatedEvent;
import by.pilipuk.service.KitchenOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KitchenOrderListener {

    private final KitchenOrderService kitchenOrderService;

    @KafkaListener(topics = "orders", groupId = "kitchen-group")
    public void listen(OrderCreatedEvent event) {
        kitchenOrderService.acceptNewOrdersFromKafka(event);
    }
}
