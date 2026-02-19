package by.pilipuk.service;

import by.pilipuk.dto.OrderDto;
import by.pilipuk.dto.OrderRequestDto;
import by.pilipuk.dto.OrderWriteDto;
import by.pilipuk.mapper.OrderMapper;
import by.pilipuk.mapper.OrderSpecificationMapper;
import by.pilipuk.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderSpecificationMapper orderSpecificationMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public ResponseEntity<OrderDto> createOrder(OrderWriteDto orderWriteDto) {
        var savedOrder = orderRepository.save(orderMapper.toEntity(orderWriteDto));

        var orderCreatedEvent = orderMapper.toOrderCreatedEvent(savedOrder);

        kafkaTemplate.send("orders", orderCreatedEvent.getId(), orderCreatedEvent)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Message sent to Kafka: {}", orderCreatedEvent);
                    } else {
                        log.error("Failed to send message to Kafka", ex);
                    }
                });
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.toDto(savedOrder));
    }

    public ResponseEntity<OrderDto> findOrderById(Long id) {
        var orderDto = orderMapper.toDto(orderRepository.findByIdOrElseThrow(id));
        return ResponseEntity.ok(orderDto);
    }

    public ResponseEntity<List<OrderDto>> findOrders(OrderRequestDto orderRequestDto) {
        var spec = orderSpecificationMapper.orderSpecification(orderRequestDto);

        var ordersDtoList = orderRepository.findAll(spec).stream().
                map(orderMapper::toDto).toList();
        if (CollectionUtils.isEmpty(ordersDtoList)) {
            throw new RuntimeException("NOT_FOUND_BY_FILTER");
        }
        else return ResponseEntity.ok(ordersDtoList);
    }
}