package by.pilipuk.orders.environment.data.dtoCreation;

import by.pilipuk.orders.dto.OrderWriteDto;
import by.pilipuk.orders.dto.OrderDto;
import by.pilipuk.orders.dto.OrderItemsWriteDto;
import by.pilipuk.orders.dto.OrderItemsDto;
import by.pilipuk.orders.dto.OrderRequestDto;
import by.pilipuk.orders.model.entity.Status;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class OrderDtosCreator {

    public OrderWriteDto createOrderWriteDto(List<OrderItemsWriteDto> items) {
        return new OrderWriteDto(1, items);
    }

    public OrderDto createOrderDto(List<OrderItemsDto> items) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.status(String.valueOf(Status.CREATED));
        orderDto.setTableNumber(1);
        orderDto.setItems(items);

        return orderDto;
    }

    public OrderRequestDto createOrderRequestDto() {
        return new OrderRequestDto();
    }
}