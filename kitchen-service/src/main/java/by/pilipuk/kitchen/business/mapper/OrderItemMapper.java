package by.pilipuk.kitchen.business.mapper;

import by.pilipuk.kitchen.dto.OrderItemsDto;
import by.pilipuk.kitchen.dto.OrderItemsWriteDto;
import by.pilipuk.kitchen.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "cooked", constant = "false")
    @Mapping(target = "name", source = "itemName")
    public abstract OrderItem toEntity(OrderItemsWriteDto kitchenOrderItemsWriteDto);

    @Mapping(target = "itemName", source = "name")
    @Mapping(target = "orderId", source = "order.id")
    public abstract OrderItemsDto toDto(OrderItem orderItem);
}
