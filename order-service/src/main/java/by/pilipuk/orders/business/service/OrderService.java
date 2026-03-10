package by.pilipuk.orders.business.service;

import by.pilipuk.orders.dto.OrderDto;
import by.pilipuk.orders.dto.OrderRequestDto;
import by.pilipuk.orders.dto.OrderWriteDto;
import by.pilipuk.orders.model.entity.Order;
import by.pilipuk.orders.model.entity.Status;
import by.pilipuk.orders.business.mapper.OrderMapper;
import by.pilipuk.orders.business.mapper.OrderSpecificationMapper;
import by.pilipuk.common.model.dto.OrderReadyEvent;
import by.pilipuk.orders.business.repository.OrderRepository;
import by.pilipuk.common.business.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderSpecificationMapper orderSpecificationMapper;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public OrderDto createOrder(OrderWriteDto orderWriteDto) {
        var mappedOrder = orderMapper.toEntity(orderWriteDto);
        var savedOrder = orderRepository.save(mappedOrder);

        var orderCreatedEvent = orderMapper.toOrderCreatedEvent(savedOrder);

        String jsonEvent = objectMapper.writeValueAsString(orderCreatedEvent);

        outboxEventRepository.save(orderMapper.toOutboxEvent(savedOrder, jsonEvent));

        log.info("The order: {} is created", savedOrder.getId());

        return orderMapper.toDto(savedOrder);
    }

    @Cacheable(value = "orders", key = "#id")
    public OrderDto findOrderById(Long id) {
        return orderMapper.toDto(orderRepository.findByIdOrElseThrow(id));
    }

    public List<OrderDto> findOrders(OrderRequestDto orderRequestDto) {
        var spec = orderSpecificationMapper.orderSpecification(orderRequestDto);

        return orderRepository.findAll(spec).stream()
                .map(orderMapper::toDto).toList();
    }

    @CacheEvict(value = "orders", key = "#orderReadyEvent.orderId")
    public void acceptCompletedOrdersFromKafka(OrderReadyEvent orderReadyEvent) {
        Order order = orderRepository.findByIdOrElseThrow(orderReadyEvent.getOrderId());
        order.setStatus(Status.READY);
        orderRepository.save(order);
        log.info("The order: {} is ready", order.getId());
    }
}