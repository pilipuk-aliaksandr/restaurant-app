package by.pilipuk.kitchen.environment.data.dtoCreation;

import by.pilipuk.kitchen.dto.OrderDto;
import by.pilipuk.kitchen.dto.OrderRequestDto;
import org.springframework.stereotype.Component;

@Component
public class OrderDtosCreator {

    public OrderDto createOrderDto() {
        var orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setOrderId(1L);
        orderDto.setStatus(OrderDto.StatusEnum.ACCEPTED);

        return orderDto;
    }

    public OrderRequestDto createOrderRequestDto() {
        return new OrderRequestDto();
    }
}