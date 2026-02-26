package by.pilipuk.service;

import by.pilipuk.entity.OutboxEvent;
import by.pilipuk.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxSender {

    private final OutboxEventRepository outboxRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    // updatedAt - нужно включить аудирование через hibernate а не вручную этим управлять
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendSingleEvent(OutboxEvent event) {
        try {
            kafkaTemplate.send(event.getTopic(), event.getOrderId(), event.getEvent()).get();

            //достаточно active - false выставить
            event.setKafkaMessageStatus(true);
            event.setUpdatedAt(LocalDateTime.now());
            outboxRepository.save(event);

            log.info("The {} event {} is sent successfully!", event.getTopic(), event.getOrderId());
        } catch (Exception e) {
            //выбрасывай своё исключение, никакого рантайма быть не должно
            log.error("Failed to send event {}: {}", event.getOrderId(), e.getMessage());
            throw new RuntimeException(e);
        }
    }
}