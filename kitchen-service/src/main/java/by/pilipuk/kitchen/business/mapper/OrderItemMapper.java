package by.pilipuk.kitchen.business.mapper;

import by.pilipuk.kitchen.dto.OrderItemsDto;
import by.pilipuk.kitchen.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderItemMapper {

    @Mapping(target = "itemName", source = "name")
    @Mapping(target = "orderId", source = "order.id")
    public abstract OrderItemsDto toDto(OrderItem orderItem);
}