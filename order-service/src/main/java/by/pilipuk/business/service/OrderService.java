package by.pilipuk.business.service;

import by.pilipuk.dto.OrderDto;
import by.pilipuk.dto.OrderRequestDto;
import by.pilipuk.dto.OrderWriteDto;
import by.pilipuk.model.entity.Order;
import by.pilipuk.model.entity.Status;
import by.pilipuk.business.mapper.OrderMapper;
import by.pilipuk.business.mapper.OrderSpecificationMapper;
import by.pilipuk.model.dto.OrderReadyEvent;
import by.pilipuk.business.repository.OrderRepository;
import by.pilipuk.business.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public OrderDto findOrderById(Long id) {
        return orderMapper.toDto(orderRepository.findByIdOrElseThrow(id));
    }

    public List<OrderDto> findOrders(OrderRequestDto orderRequestDto) {
        var spec = orderSpecificationMapper.orderSpecification(orderRequestDto);

        var ordersDtoList = orderRepository.findAll(spec).stream()
                .map(orderMapper::toDto).toList();

        return ordersDtoList;
    }

    public void acceptCompletedOrdersFromKafka(OrderReadyEvent orderReadyEvent) {
        Order order = orderRepository.findByIdOrElseThrow(orderReadyEvent.getOrderId());
        order.setStatus(Status.READY);
        orderRepository.save(order);
        log.info("The order: {} is ready", order.getId());
    }
}