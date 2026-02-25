package by.pilipuk.service;

import by.pilipuk.entity.OutboxEvent;
import by.pilipuk.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutboxSchedulerService {
    private final OutboxEventRepository outboxRepository;
    private final OutboxSender outboxSender;

    @Scheduled(fixedDelay = 5000)
    public void processOutbox() {
        var events = outboxRepository.findAllByKafkaMessageStatusFalse();

        for (OutboxEvent event : events) {
            outboxSender.sendSingleEvent(event);
        }
    }
}