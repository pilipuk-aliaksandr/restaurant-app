package by.pilipuk.service;

import by.pilipuk.dto.KitchenOrderDto;
import by.pilipuk.dto.OrderCreatedEvent;
import by.pilipuk.entity.KitchenOrder;
import by.pilipuk.entity.Status;
import by.pilipuk.mapper.KitchenOrderMapper;
import by.pilipuk.repository.KitchenOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenOrderConsumer {

    private final KitchenOrderRepository orderRepository;
    private final KitchenOrderMapper orderMapper;

    @KafkaListener(topics = "orders", groupId = "kitchen-group")
    @Transactional
    public void consume(OrderCreatedEvent event) {
        KitchenOrder kitchenOrder = orderMapper.toEntity(event);
        kitchenOrder.setStatus(Status.ACCEPTED);
        orderRepository.save(kitchenOrder);
        log.info("Accepted new order: {} in cooking process", kitchenOrder.getOrderId());
    }
}
