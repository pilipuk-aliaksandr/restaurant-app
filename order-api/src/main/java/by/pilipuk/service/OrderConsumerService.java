package by.pilipuk.service;

import by.pilipuk.dto.OrderCreatedEvent;
import by.pilipuk.entity.Order;
import by.pilipuk.entity.Status;
import by.pilipuk.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderConsumerService {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "ready_orders", groupId = "orders-group")
    @Transactional("transactionManager")
    public void consume(OrderCreatedEvent event) {
        Order order = orderRepository.findByIdOrElseThrow(event.getOrderId());
        order.setStatus(Status.READY);
        orderRepository.save(order);
        log.info("The order: {} is ready", order.getId());
    }
}
