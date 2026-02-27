package by.pilipuk.entrypoint;

import by.pilipuk.model.dto.OrderCreatedEvent;
import by.pilipuk.business.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KitchenListener {

    private final KitchenService kitchenService;

    @KafkaListener(topics = "orders", groupId = "kitchen-group")
    public void listen(OrderCreatedEvent event) {
        kitchenService.acceptNewOrdersFromKafka(event);
    }
}
