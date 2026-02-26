package by.pilipuk.service;
//сделай нормальный пакет by.pilipuk.kitchen
// by.pilipuk.order
// by.pilipuk.common
import by.pilipuk.entity.KitchenOrder;
import by.pilipuk.entity.KitchenOrderItem;
import by.pilipuk.entity.Status;
import by.pilipuk.mapper.KitchenOrderMapper;
import by.pilipuk.repository.KitchenOrderItemRepository;
import by.pilipuk.repository.KitchenOrderRepository;
import by.pilipuk.repository.OutboxEventRepository;
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
public class KitchenOrderCookerService {

    private final KitchenOrderRepository orderRepository;
    private final KitchenOrderMapper kitchenOrderMapper;
    private final KitchenOrderItemRepository itemRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 10000) // Every 10 seconds service cooks only one meal (item)
    @Transactional
    public void cookOneItem() {
        itemRepository.findFirstByCookedFalseOrderByCreatedAtAsc()
                .ifPresent(item -> {
                    item.setCooked(true);
                    itemRepository.save(item);

                    log.info("Item: {} for order {} is cooked {}", item.getName(), item.getKitchenOrder().getOrderId(), LocalDateTime.now());

                    KitchenOrder kitchenOrder = item.getKitchenOrder();
                    if (kitchenOrder.getStatus() == Status.ACCEPTED) {
                        kitchenOrder.setStatus(Status.COOKING);
                    }

                    boolean allDone = kitchenOrder.getItems().stream().allMatch(KitchenOrderItem::isCooked);
                    if (allDone) {
                        kitchenOrder.setStatus(Status.COMPLETED);
                        kitchenOrder.setCompletedAt(LocalDateTime.now());
                        var savedKitchenOrder = orderRepository.save(kitchenOrder);

                        log.info("Order: {} is cooked {}", kitchenOrder.getOrderId(), kitchenOrder.getCompletedAt());

                        String jsonEvent = objectMapper.writeValueAsString(kitchenOrderMapper.toOrderReadyEvent(savedKitchenOrder));

                        outboxEventRepository.save(kitchenOrderMapper.toOutboxEvent(savedKitchenOrder, jsonEvent));
                    }
                });
    }
}