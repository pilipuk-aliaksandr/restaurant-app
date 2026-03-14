package by.pilipuk.kitchen.business.service;

import by.pilipuk.kitchen.business.mapper.OrderMapper;
import by.pilipuk.kitchen.dto.OrderDto;
import by.pilipuk.kitchen.dto.OrderRequestDto;
import by.pilipuk.commonKafka.model.dto.OrderCreatedEvent;
import by.pilipuk.kitchen.model.entity.Order;
import by.pilipuk.kitchen.model.entity.Status;
import by.pilipuk.kitchen.business.mapper.OrderSpecificationMapper;
import by.pilipuk.kitchen.business.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderSpecificationMapper orderSpecificationMapper;

    @Cacheable(value = "kitchen", key = "#id")
    public OrderDto findOrderById(Long id) {
        return orderMapper.toDto(orderRepository.findByIdOrElseThrow(id));
    }

    public List<OrderDto> findOrders(OrderRequestDto kitchenRequestDto) {
        var spec = orderSpecificationMapper.kitchenOrderSpecification(kitchenRequestDto);

        return orderRepository.findAll(spec).stream()
                .map(orderMapper::toDto).toList();
    }

    @Transactional
    public void acceptNewOrdersFromKafka(OrderCreatedEvent event) {
        Order order = orderMapper.toEntity(event);
        order.setStatus(Status.ACCEPTED);
        orderRepository.save(order);
        log.info("Accepted new order: {} in cooking process", order.getOrderId());
    }
}