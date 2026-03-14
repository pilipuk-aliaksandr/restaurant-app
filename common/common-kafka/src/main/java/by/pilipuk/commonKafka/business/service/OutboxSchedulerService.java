package by.pilipuk.commonKafka.business.service;

import by.pilipuk.commonKafka.business.repository.OutboxEventRepository;
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
        for (int i = 0; i < 50; i++) {
            try {
                boolean processed = outboxSender.sendSingleEvent();
                if (!processed) {
                    break;
                }
            } catch (Exception e) {
                break;
            }
        }
    }
}