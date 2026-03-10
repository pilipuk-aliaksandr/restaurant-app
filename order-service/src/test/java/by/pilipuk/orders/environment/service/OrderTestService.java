package by.pilipuk.orders.environment.service;

import by.pilipuk.orders.business.mapper.OrderMapper;
import by.pilipuk.orders.business.repository.OrderRepository;
import by.pilipuk.orders.dto.OrderDto;
import by.pilipuk.orders.dto.OrderRequestDto;
import by.pilipuk.orders.dto.OrderWriteDto;
import by.pilipuk.orders.environment.data.dtoCreation.DtosCreator;
import by.pilipuk.orders.environment.data.entityCreation.EntityCreator;
import by.pilipuk.orders.environment.data.eventCreation.EventCreator;
import by.pilipuk.common.model.dto.OrderReadyEvent;
import by.pilipuk.orders.model.entity.Order;
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
        var orderItems = entityCreator.orderItemsCreator.createOrderItems(order);
        order.setItems(orderItems);
        return order;
    }

    public void saveOrder() {
        var order = entityCreator.orderCreator.saveTestOrderToDB(
                entityCreator.orderCreator.createOrder()
        );
        var orderItems = entityCreator.orderItemsCreator.saveTestOrderItemsToDB(order);
        order.setItems(orderItems);
    }

    public OrderWriteDto createOrderWriteDto() {
        var orderItemsWriteDto = dtosCreator.orderItemsDtosCreator.createOrderItemsWriteDto();
        return dtosCreator.orderDtosCreator.createOrderWriteDto(orderItemsWriteDto);
    }

    public OrderDto createOrderDto() {
        var orderItemsDto = dtosCreator.orderItemsDtosCreator.createOrderItemsDto();
        return dtosCreator.orderDtosCreator.createOrderDto(orderItemsDto);
    }

    public OrderRequestDto createOrderRequestDto() {
        return dtosCreator.orderDtosCreator.createOrderRequestDto();
    }

    public OrderReadyEvent createOrderReadyEvent() {
        return eventCreator.createOrderReadyEvent();
    }

    @Transactional
    public OrderDto getOrderDtoFromDB() {
        var savedOrder = orderRepository.findByIdOrElseThrow(1L);
        Hibernate.initialize(savedOrder.getItems());

        return orderMapper.toDto(savedOrder);
    }
}