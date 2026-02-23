package by.pilipuk.service;

import by.pilipuk.dto.OrderReadyEvent;
import by.pilipuk.entity.KitchenOrder;
import by.pilipuk.entity.KitchenOrderItem;
import by.pilipuk.entity.Status;
import by.pilipuk.mapper.KitchenOrderMapper;
import by.pilipuk.repository.KitchenOrderItemRepository;
import by.pilipuk.repository.KitchenOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenOrderCooker {

    private final KitchenOrderRepository orderRepository;
    private final KitchenOrderMapper kitchenOrderMapper;
    private final KitchenOrderItemRepository itemRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Scheduled(fixedDelay = 10000) // Every 10 seconds service cooks only one meal (item)
    @Transactional
    public void cookOneItem() {
        itemRepository.findFirstByCookedFalseOrderByCreatedAtAsc()
                .ifPresent(item -> {
                    item.setCooked(true);
                    itemRepository.save(item);

                    // Later need to move all logs logic with statusChanging in one LoggingService
                    log.info("Item: {} for order {} is cooked {}", item.getName(), item.getKitchenOrder().getOrderId(), LocalDateTime.now());

                    KitchenOrder kitchenOrder = item.getKitchenOrder();
                    if (kitchenOrder.getStatus() == Status.ACCEPTED) {
                        kitchenOrder.setStatus(Status.COOKING);
                    }

                    boolean allDone = kitchenOrder.getItems().stream().allMatch(KitchenOrderItem::isCooked);
                    if (allDone) {
                        kitchenOrder.setStatus(Status.COMPLETED);
                        kitchenOrder.setCompletedAt(LocalDateTime.now());
                        orderRepository.save(kitchenOrder);

                        // Later need to move all logs logic with statusChanging in one LoggingService
                        log.info("Order: {} is cooked {}", kitchenOrder.getOrderId(), kitchenOrder.getCompletedAt());

                        sendOrderReadyEvent(kitchenOrderMapper.toOrderReadyEvent(kitchenOrder));
                    }
                });
    }

    private void sendOrderReadyEvent(OrderReadyEvent orderReadyEvent) {
        kafkaTemplate.send("ready_orders", orderReadyEvent);
    }
}