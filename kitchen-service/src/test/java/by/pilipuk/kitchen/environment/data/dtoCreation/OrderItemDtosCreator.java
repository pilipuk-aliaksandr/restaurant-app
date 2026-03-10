package by.pilipuk.kitchen.environment.data.dtoCreation;

import by.pilipuk.kitchen.dto.OrderDto;
import by.pilipuk.kitchen.dto.OrderItemsDto;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class OrderItemDtosCreator {

    private OrderItemsDto createOrderItemDto(Long id, OrderDto orderDto, String name) {
        var orderItemDto = new OrderItemsDto();
        orderItemDto.setId(id);
        orderItemDto.setOrderId(orderDto.getId());
        orderItemDto.setItemName(name);
        orderItemDto.setCooked(false);

        return orderItemDto;
    }

    public List<OrderItemsDto> createOrderItemsDto(OrderDto orderDto) {
        return List.of(
                createOrderItemDto(1L, orderDto, "Pizza"),
                createOrderItemDto(2L, orderDto, "Coca-Cola")
        );
    }
}