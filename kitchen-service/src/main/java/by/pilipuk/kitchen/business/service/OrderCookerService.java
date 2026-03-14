package by.pilipuk.kitchen.business.service;

import by.pilipuk.kitchen.model.entity.Order;
import by.pilipuk.kitchen.model.entity.OrderItem;
import by.pilipuk.kitchen.model.entity.Status;
import by.pilipuk.kitchen.business.mapper.OrderMapper;
import by.pilipuk.kitchen.business.repository.OrderItemRepository;
import by.pilipuk.kitchen.business.repository.OrderRepository;
import by.pilipuk.commonKafka.business.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCookerService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemRepository itemRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;
    private final CacheManager cacheManager;

    @Scheduled(fixedDelay = 10000) // Every 10 seconds service cooks only one meal (item)
    @Transactional
    @CacheEvict(value = "kitchen", key = "#result", condition = "#result != null")
    public void cookOneItem() {
        itemRepository.findFirstByCookedFalseOrderByCreatedAtAsc()
                .ifPresent(item -> {
                    item.setCooked(true);
                    itemRepository.save(item);

                    log.info("Item: {} for order {} is cooked {}", item.getName(), item.getOrder().getOrderId(), LocalDateTime.now());

                    Order order = item.getOrder();
                    if (order.getStatus() == Status.ACCEPTED) {
                        order.setStatus(Status.COOKING);
                    }

                    Objects.requireNonNull(cacheManager.getCache("kitchen")).evict(order.getId());

                    boolean allDone = order.getItems().stream().allMatch(OrderItem::isCooked);
                    if (allDone) {
                        order.setStatus(Status.COMPLETED);
                        var savedKitchenOrder = orderRepository.save(order);

                        log.info("Order: {} is cooked {}", order.getOrderId(), order.getUpdatedAt());

                        Objects.requireNonNull(cacheManager.getCache("kitchen")).evict(order.getId());

                        String jsonEvent = objectMapper.writeValueAsString(orderMapper.toOrderReadyEvent(savedKitchenOrder));

                        outboxEventRepository.save(orderMapper.toOutboxEvent(savedKitchenOrder, jsonEvent));
                    }
                });
    }
}