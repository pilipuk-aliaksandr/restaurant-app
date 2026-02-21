package by.pilipuk.service;

import by.pilipuk.dto.OrderDto;
import by.pilipuk.dto.OrderRequestDto;
import by.pilipuk.dto.OrderWriteDto;
import by.pilipuk.exception.ValidationException;
import by.pilipuk.mapper.OrderMapper;
import by.pilipuk.mapper.OrderSpecificationMapper;
import by.pilipuk.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.List;

import static by.pilipuk.exception.ValidationCode.NOT_FOUND_BY_FILTER;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderSpecificationMapper orderSpecificationMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /* 1. Later need to separate createOrderEvent to Kafka and createOrder to DB within a transaction
    so that createOrderEvent occurs only after the data is successfully written to the DB */
    @Transactional
    public OrderDto createOrder(OrderWriteDto orderWriteDto) {
        var savedOrder = orderRepository.save(orderMapper.toEntity(orderWriteDto));

        var orderCreatedEvent = orderMapper.toOrderCreatedEvent(savedOrder);

        kafkaTemplate.send("orders", orderCreatedEvent.getId().toString(), orderCreatedEvent)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Message sent to Kafka: {}", orderCreatedEvent);
                    } else {
                        log.error("Failed to send message to Kafka", ex);
                    }
                });
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
}