package by.pilipuk.kitchen.environment.service;

import by.pilipuk.kitchen.business.mapper.OrderMapper;
import by.pilipuk.kitchen.business.repository.OrderRepository;
import by.pilipuk.kitchen.dto.OrderDto;
import by.pilipuk.kitchen.environment.data.dtoCreation.DtosCreator;
import by.pilipuk.kitchen.environment.data.entityCreation.EntityCreator;
import by.pilipuk.kitchen.environment.data.eventCreation.EventCreator;
import by.pilipuk.kitchen.dto.OrderRequestDto;
import by.pilipuk.kitchen.model.entity.Order;
import by.pilipuk.common.model.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderTestService {

    private final EntityCreator entityCreator;
    private final DtosCreator dtosCreator;
    private final EventCreator eventCreator;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Order createOrder() {
        var order = entityCreator.orderCreator.createOrder();
        var orderItems = entityCreator.orderItemCreator.createOrderItems(order);
        order.setItems(orderItems);

        return order;
    }

    public void saveOrder() {
        var order = entityCreator.orderCreator.saveOrder(
                entityCreator.orderCreator.createOrder()
        );
        var orderItems = entityCreator.orderItemCreator.saveOrderItems(order);
        order.setItems(orderItems);
    }

    public OrderDto createOrderDto() {
        var orderDto = dtosCreator.orderDtosCreator.createOrderDto();
        var orderItemsDto = dtosCreator.orderItemDtosCreator.createOrderItemsDto(orderDto);
        orderDto.setItems(orderItemsDto);

        return orderDto;
    }

    public OrderRequestDto createOrderRequestDto() {
        return dtosCreator.orderDtosCreator.createOrderRequestDto();
    }

    public OrderCreatedEvent createOrderCreatedEvent() {
        return eventCreator.createOrderCreatedEvent();
    }

    @Transactional
    public OrderDto getOrderDtoFromDB() {
        var savedOrder = orderRepository.findByIdOrElseThrow(1L);
        Hibernate.initialize(savedOrder.getItems());
        return orderMapper.toDto(savedOrder);
    }
}