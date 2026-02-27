package by.pilipuk.business.mapper;

import by.pilipuk.dto.KitchenItemsDto;
import by.pilipuk.dto.KitchenItemsWriteDto;
import by.pilipuk.model.entity.KitchenItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class KitchenItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kitchen", ignore = true)
    @Mapping(target = "cooked", constant = "false")
    @Mapping(target = "name", source = "itemName")
    public abstract KitchenItem toEntity(KitchenItemsWriteDto kitchenOrderItemsWriteDto);

    @Mapping(target = "itemName", source = "name")
    @Mapping(target = "kitchenId", source = "kitchen.id")
    public abstract KitchenItemsDto toDto(KitchenItem kitchenItem);
}
