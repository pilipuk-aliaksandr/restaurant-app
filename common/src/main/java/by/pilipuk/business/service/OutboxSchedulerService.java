package by.pilipuk.business.service;

import by.pilipuk.business.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutboxSchedulerService {
    private final OutboxEventRepository outboxRepository;
    private final OutboxSender outboxSender;

    //тебе надо селектить запись через FOR UPDATE SKIPPED LOCKED и по одной и после отправки деактивировать запись!
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