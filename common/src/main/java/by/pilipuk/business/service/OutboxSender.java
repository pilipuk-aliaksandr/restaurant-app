package by.pilipuk.business.service;

import by.pilipuk.business.repository.OutboxEventRepository;
import by.pilipuk.core.exception.base.OutboxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import static by.pilipuk.core.exception.base.OutboxExceptionCode.FAILED_MESSAGING_TO_KAFKA;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxSender {

    private final OutboxEventRepository outboxRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    // updatedAt - нужно включить аудирование через hibernate а не вручную этим управлять
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean sendSingleEvent() {
        return outboxRepository.findNextForProcessing()
                .map(event -> {
                    try {
                        kafkaTemplate.send(event.getTopic(), event.getKeyOrderId(), event.getPayload()).get();

                        event.setActive(false);
                        outboxRepository.save(event);
                        return true;

                    } catch (Exception e) {
                        log.error("Failed to send event {}: {}", event.getKeyOrderId(), e.getMessage());
                        throw OutboxException.create(FAILED_MESSAGING_TO_KAFKA, e);
                    }
                }).orElse(false);
    }
}