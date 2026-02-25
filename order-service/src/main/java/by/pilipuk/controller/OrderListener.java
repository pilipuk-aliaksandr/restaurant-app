package by.pilipuk.controller;

import by.pilipuk.dto.OrderReadyEvent;
import by.pilipuk.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderListener {

    private final OrderService orderService;

    @KafkaListener(topics = "ready_orders", groupId = "orders-group")
    public void listen(OrderReadyEvent orderReadyEvent) {
        orderService.acceptCompletedOrdersFromKafka(orderReadyEvent);
    }

}
