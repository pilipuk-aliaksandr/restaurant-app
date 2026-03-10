package by.pilipuk.environment.data.dtoCreation;

import by.pilipuk.dto.OrderItemsDto;
import by.pilipuk.dto.OrderItemsWriteDto;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class OrderItemsDtosCreator {

    private OrderItemsWriteDto createOrderItemWriteDto(String name) {
        return new OrderItemsWriteDto(name);
    }

    public List<OrderItemsWriteDto> createOrderItemsWriteDto() {
        return List.of(
                createOrderItemWriteDto("Pizza"),
                createOrderItemWriteDto("Coca-Cola"));
    }

    private OrderItemsDto createOrderItemDto(Long id, String name) {
        OrderItemsDto orderItemsDto = new OrderItemsDto();
        orderItemsDto.setId(id);
        orderItemsDto.setItemName(name);

        return orderItemsDto;
    }

    public List<OrderItemsDto> createOrderItemsDto() {
        return List.of(
                createOrderItemDto(1L, "Pizza"),
                createOrderItemDto(2L, "Coca-Cola"));
    }
}