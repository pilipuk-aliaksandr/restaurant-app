package by.pilipuk.mapper;

import by.pilipuk.dto.KitchenOrderItemsDto;
import by.pilipuk.dto.KitchenOrderItemsWriteDto;
import by.pilipuk.entity.KitchenOrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class KitchenOrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kitchenOrder", ignore = true)
    @Mapping(target = "cooked", constant = "false")
    @Mapping(target = "name", source = "itemName")
    public abstract KitchenOrderItem toEntity(KitchenOrderItemsWriteDto kitchenOrderItemsWriteDto);

    @Mapping(target = "itemName", source = "name")
    public abstract KitchenOrderItemsDto toDto(KitchenOrderItem kitchenOrderItem);
}
