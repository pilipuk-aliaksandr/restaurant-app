package by.pilipuk.environment.data.dtoCreation;

import by.pilipuk.dto.OrderWriteDto;
import by.pilipuk.dto.OrderDto;
import by.pilipuk.dto.OrderItemsWriteDto;
import by.pilipuk.dto.OrderItemsDto;
import by.pilipuk.dto.OrderRequestDto;
import by.pilipuk.model.entity.Status;
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