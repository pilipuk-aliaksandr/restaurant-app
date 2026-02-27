package by.pilipuk.business.service;
//сделай нормальный пакет by.pilipuk.kitchen
// by.pilipuk.order
// by.pilipuk.common
import by.pilipuk.model.entity.Kitchen;
import by.pilipuk.model.entity.KitchenItem;
import by.pilipuk.model.entity.Status;
import by.pilipuk.business.mapper.KitchenMapper;
import by.pilipuk.business.repository.KitchenItemRepository;
import by.pilipuk.business.repository.KitchenRepository;
import by.pilipuk.business.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenCookerService {

    private final KitchenRepository orderRepository;
    private final KitchenMapper kitchenMapper;
    private final KitchenItemRepository itemRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 10000) // Every 10 seconds service cooks only one meal (item)
    @Transactional
    public void cookOneItem() {
        itemRepository.findFirstByCookedFalseOrderByCreatedAtAsc()
                .ifPresent(item -> {
                    item.setCooked(true);
                    itemRepository.save(item);

                    log.info("Item: {} for order {} is cooked {}", item.getName(), item.getKitchen().getOrderId(), LocalDateTime.now());

                    Kitchen kitchen = item.getKitchen();
                    if (kitchen.getStatus() == Status.ACCEPTED) {
                        kitchen.setStatus(Status.COOKING);
                    }

                    boolean allDone = kitchen.getItems().stream().allMatch(KitchenItem::isCooked);
                    if (allDone) {
                        kitchen.setStatus(Status.COMPLETED);
                        var savedKitchenOrder = orderRepository.save(kitchen);

                        log.info("Order: {} is cooked {}", kitchen.getOrderId(), kitchen.getUpdatedAt());

                        String jsonEvent = objectMapper.writeValueAsString(kitchenMapper.toOrderReadyEvent(savedKitchenOrder));

                        outboxEventRepository.save(kitchenMapper.toOutboxEvent(savedKitchenOrder, jsonEvent));
                    }
                });
    }
}