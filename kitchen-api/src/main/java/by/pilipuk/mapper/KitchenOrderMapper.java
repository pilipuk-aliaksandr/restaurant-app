package by.pilipuk.mapper;

import by.pilipuk.dto.KitchenOrderDto;
import by.pilipuk.dto.KitchenOrderWriteDto;
import by.pilipuk.entity.KitchenOrder;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper(
        componentModel = "spring",
        uses = {KitchenOrderItemMapper.class}
)
public abstract class KitchenOrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "status", constant = "ACCEPTED")
    @Mapping(target = "items", source = "items")
    public abstract KitchenOrder toEntity(KitchenOrderWriteDto kitchenOrderWriteDto);

    @AfterMapping
    protected void getItems(@MappingTarget KitchenOrder kitchenOrder) {
        if (kitchenOrder.getItems() != null) {
            kitchenOrder.getItems().forEach(item -> item.setKitchenOrder(kitchenOrder));
        }
    }

    public abstract KitchenOrderDto toDto(KitchenOrder kitchenOrder);
}