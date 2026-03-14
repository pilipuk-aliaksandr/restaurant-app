package by.pilipuk.kitchen.entrypoint.cron;

import by.pilipuk.kitchen.business.service.KitchenService;
import by.pilipuk.kitchen.business.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenCron {

    private final KitchenService kitchenService;
    private final OrderItemRepository itemRepository;

    @Scheduled(fixedDelay = 10000) // Every 10 seconds cron cooks only one meal (item)
    @CacheEvict(value = "kitchen", key = "#result", condition = "#result != null")
    @Transactional
    public void cookOneItem() {
        itemRepository.findFirstByCookedFalseOrderByCreatedAtAsc()
                .ifPresent(kitchenService::processed);
    }
}