package by.pilipuk.service;

import by.pilipuk.dto.*;
import by.pilipuk.entity.Order;
import by.pilipuk.entity.Status;
import by.pilipuk.exception.ValidationException;
import by.pilipuk.mapper.OrderMapper;
import by.pilipuk.mapper.OrderSpecificationMapper;
import by.pilipuk.repository.OrderRepository;
import by.pilipuk.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tools.jackson.databind.ObjectMapper;
import java.util.List;
import static by.pilipuk.exception.ValidationCode.NOT_FOUND_BY_FILTER;

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
        savedOrder.setStatus(Status.PENDING);

        var orderCreatedEvent = orderMapper.toOrderCreatedEvent(savedOrder);

        String jsonEvent = objectMapper.writeValueAsString(orderCreatedEvent);

        outboxEventRepository.save(orderMapper.toOutboxEvent(savedOrder, jsonEvent));

        log.info("The order: {} is created", savedOrder.getId());

        return orderMapper.toDto(savedOrder);
    }

    public OrderDto findOrderById(Long id) {
        return orderMapper.toDto(orderRepository.findByIdOrElseThrow(id));
    }

    /* 2. Also later need to add ExceptionDetails and delete substring */
    public List<OrderDto> findOrders(OrderRequestDto orderRequestDto) {
        var spec = orderSpecificationMapper.orderSpecification(orderRequestDto);

        var ordersDtoList = orderRepository.findAll(spec).stream()
                .map(orderMapper::toDto).toList();
        if (CollectionUtils.isEmpty(ordersDtoList)) {
            throw ValidationException.create(NOT_FOUND_BY_FILTER, orderRequestDto.toString().substring(22));
        }
        else return ordersDtoList;
    }

    public void acceptCompletedOrdersFromKafka(OrderReadyEvent orderReadyEvent) {
        Order order = orderRepository.findByIdOrElseThrow(orderReadyEvent.getOrderId());
        order.setStatus(Status.READY);
        orderRepository.save(order);
        log.info("The order: {} is ready", order.getId());
    }
}