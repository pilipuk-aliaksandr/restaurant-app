package by.pilipuk.business.service;

import by.pilipuk.dto.*;
import by.pilipuk.exception.OrderException;
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
import org.springframework.util.CollectionUtils;
import tools.jackson.databind.ObjectMapper;
import java.util.List;
import static by.pilipuk.exception.OrderExceptionCode.NOT_FOUND_BY_FILTER;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderSpecificationMapper orderSpecificationMapper;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    /*
    зачем ставить статус CREATED и в той же транзакции заменять его на PENDING?
    PENDING - удалить
    зачем тебе jsonEvent весь объект ордера? в данном случае тебе достаточно
     */
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

    /* 2. Also later need to add ExceptionDetails and delete substring */
    public List<OrderDto> findOrders(OrderRequestDto orderRequestDto) {
        var spec = orderSpecificationMapper.orderSpecification(orderRequestDto);

        var ordersDtoList = orderRepository.findAll(spec).stream()
                .map(orderMapper::toDto).toList();

        //TODO сомнительная валидация - удалить
        if (CollectionUtils.isEmpty(ordersDtoList)) {
            throw OrderException.create(NOT_FOUND_BY_FILTER, orderRequestDto.toString());
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