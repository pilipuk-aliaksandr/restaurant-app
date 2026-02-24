package by.pilipuk.service;

import by.pilipuk.dto.OrderReadyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SendToKafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional(value = "kafkaTransactionManager")
    public void sendToKafka(OrderReadyEvent orderReadyEvent)  {
        kafkaTemplate.send("ready_orders", orderReadyEvent);
    }
}
