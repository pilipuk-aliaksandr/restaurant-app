package by.pilipuk.mapper;

import by.pilipuk.dto.OrderItemsDto;
import by.pilipuk.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "name", source = "itemName")
    public abstract OrderItem toEntity(OrderItemsDto orderItemDto);
}