package by.pilipuk.service;

import by.pilipuk.dto.OrderCreatedEvent;
import by.pilipuk.dto.OrderDto;
import by.pilipuk.dto.OrderRequestDto;
import by.pilipuk.dto.OrderWriteDto;
import by.pilipuk.entity.Status;
import by.pilipuk.exception.ValidationException;
import by.pilipuk.mapper.OrderMapper;
import by.pilipuk.mapper.OrderSpecificationMapper;
import by.pilipuk.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
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
    private final SendToKafkaService sendToKafkaService;

    @Transactional(value = "transactionManager")
    public OrderDto createOrder(OrderWriteDto orderWriteDto) {
        var mappedOrder = orderMapper.toEntity(orderWriteDto);
        mappedOrder.setStatus(Status.SENT_TO_KITCHEN);
        var savedOrder = orderRepository.save(mappedOrder);

        var orderCreatedEvent = orderMapper.toOrderCreatedEvent(savedOrder);

        sendToKafkaService.sendToKafka(orderCreatedEvent);

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