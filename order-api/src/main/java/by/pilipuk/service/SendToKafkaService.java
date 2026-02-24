package by.pilipuk.service;

import by.pilipuk.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SendToKafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional(value = "kafkaTransactionManager")
    public void sendToKafka(OrderCreatedEvent orderCreatedEvent) {
        kafkaTemplate.send("orders", orderCreatedEvent);
    }
}
