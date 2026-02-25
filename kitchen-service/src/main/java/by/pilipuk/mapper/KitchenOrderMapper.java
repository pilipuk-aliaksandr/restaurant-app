package by.pilipuk.mapper;

import by.pilipuk.dto.KitchenOrderDto;
import by.pilipuk.dto.KitchenOrderWriteDto;
import by.pilipuk.dto.OrderCreatedEvent;
import by.pilipuk.dto.OrderReadyEvent;
import by.pilipuk.entity.KitchenOrder;
import by.pilipuk.entity.KitchenOrderItem;
import by.pilipuk.entity.OutboxEvent;
import by.pilipuk.entity.Status;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        uses = {KitchenOrderItemMapper.class},
        imports = {UUID.class, LocalDateTime.class, Status.class}
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
    protected void mapItemsToNames(KitchenOrder kitchenOrder, @MappingTarget OrderReadyEvent event) {
        if (!CollectionUtils.isEmpty(kitchenOrder.getItems())) {
            List<String> itemNames = kitchenOrder.getItems().stream()
                    .map(KitchenOrderItem::getName)
                    .toList();
            event.setItems(itemNames);
        }
    }

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "orderId", source = "kitchenOrder.id")
    @Mapping(target = "topic", constant = "ready_orders")
    @Mapping(target = "event", source = "jsonEvent")
    @Mapping(target = "kafkaMessageStatus", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    public abstract OutboxEvent toOutboxEvent(KitchenOrder kitchenOrder, String jsonEvent);
}