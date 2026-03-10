package by.pilipuk.orders.business.mapper;

import by.pilipuk.orders.dto.OrderItemsDto;
import by.pilipuk.orders.dto.OrderItemsWriteDto;
import by.pilipuk.orders.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "name", source = "itemName")
    public abstract OrderItem toEntity(OrderItemsWriteDto orderItemsWriteDto);

    @Mapping(target = "itemName", source = "name")
    public abstract OrderItemsDto toDto(OrderItem orderItem);
}