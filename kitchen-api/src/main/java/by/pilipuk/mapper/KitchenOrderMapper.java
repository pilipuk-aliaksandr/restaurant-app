package by.pilipuk.mapper;

import by.pilipuk.dto.KitchenOrderDto;
import by.pilipuk.dto.KitchenOrderWriteDto;
import by.pilipuk.dto.OrderCreatedEvent;
import by.pilipuk.dto.OrderReadyEvent;
import by.pilipuk.entity.KitchenOrder;
import by.pilipuk.entity.KitchenOrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {KitchenOrderItemMapper.class}
)
public abstract class KitchenOrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "status", constant = "ACCEPTED")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "completedAt", ignore = true)
    public abstract KitchenOrder toEntity(KitchenOrderWriteDto kitchenOrderWriteDto);

    @AfterMapping
    protected void getItems(@MappingTarget KitchenOrder kitchenOrder) {
        if (kitchenOrder.getItems() != null) {
            kitchenOrder.getItems().forEach(item -> item.setKitchenOrder(kitchenOrder));
        }
    }

    public abstract KitchenOrderDto toDto(KitchenOrder kitchenOrder);

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    public abstract KitchenOrder toEntity(OrderCreatedEvent orderCreatedEvent);

    @AfterMapping
    protected void fillItems(OrderCreatedEvent event, @MappingTarget KitchenOrder kitchenOrder) {
        if (!CollectionUtils.isEmpty(event.getItems())) {
            List<KitchenOrderItem> kitchenItems = event.getItems().stream()
                    .map(name -> {
                        KitchenOrderItem item = new KitchenOrderItem();
                        item.setName(name);
                        item.setKitchenOrder(kitchenOrder);
                        return item;
                    })
                    .toList();
            kitchenOrder.setItems(kitchenItems);
        }
    }

    @Mapping(target = "items", ignore = true)
    public abstract OrderReadyEvent toOrderReadyEvent(KitchenOrder kitchenOrder);

    @AfterMapping
    protected void mapItemsToNames(OrderReadyEvent event, @MappingTarget KitchenOrder kitchenOrder) {
        if (!CollectionUtils.isEmpty(kitchenOrder.getItems())) {
            List<String> itemNames = kitchenOrder.getItems().stream()
                    .map(KitchenOrderItem::getName)
                    .toList();
            event.setItems(itemNames);
        }
    }
}