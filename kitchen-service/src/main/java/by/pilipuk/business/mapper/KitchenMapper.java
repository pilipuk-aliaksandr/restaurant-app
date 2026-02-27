package by.pilipuk.business.mapper;

import by.pilipuk.dto.KitchenDto;
import by.pilipuk.dto.KitchenWriteDto;
import by.pilipuk.model.dto.OrderCreatedEvent;
import by.pilipuk.model.dto.OrderReadyEvent;
import by.pilipuk.model.entity.Kitchen;
import by.pilipuk.model.entity.KitchenItem;
import by.pilipuk.model.entity.OutboxEvent;
import by.pilipuk.model.entity.Status;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {KitchenItemMapper.class},
        imports = {LocalDateTime.class, Status.class}
)
public abstract class KitchenMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "status", constant = "ACCEPTED")
    @Mapping(target = "items", source = "items")
    public abstract Kitchen toEntity(KitchenWriteDto kitchenOrderWriteDto);

    @AfterMapping
    protected void getItems(@MappingTarget Kitchen kitchen) {
        if (kitchen.getItems() != null) {
            kitchen.getItems().forEach(item -> item.setKitchen(kitchen));
        }
    }

    public abstract KitchenDto toDto(Kitchen kitchen);

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "items", ignore = true)
    public abstract Kitchen toEntity(OrderCreatedEvent orderCreatedEvent);

    @AfterMapping
    protected void fillItems(OrderCreatedEvent event, @MappingTarget Kitchen kitchen) {
        if (!CollectionUtils.isEmpty(event.getItems())) {
            List<KitchenItem> kitchenItems = event.getItems().stream()
                    .map(name -> {
                        KitchenItem item = new KitchenItem();
                        item.setName(name);
                        item.setKitchen(kitchen);
                        return item;
                    })
                    .toList();
            kitchen.setItems(kitchenItems);
        }
    }

    @Mapping(target = "items", ignore = true)
    public abstract OrderReadyEvent toOrderReadyEvent(Kitchen kitchen);

    @AfterMapping
    protected void mapItemsToNames(Kitchen kitchen, @MappingTarget OrderReadyEvent event) {
        if (!CollectionUtils.isEmpty(kitchen.getItems())) {
            List<String> itemNames = kitchen.getItems().stream()
                    .map(KitchenItem::getName)
                    .toList();
            event.setItems(itemNames);
        }
    }

    @Mapping(target = "keyOrderId", source = "kitchen.id")
    @Mapping(target = "topic", constant = "ready_orders")
    @Mapping(target = "payload", source = "jsonEvent")
    public abstract OutboxEvent toOutboxEvent(Kitchen kitchen, String jsonEvent);
}