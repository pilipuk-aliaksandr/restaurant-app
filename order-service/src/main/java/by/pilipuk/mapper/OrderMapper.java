package by.pilipuk.mapper;

import by.pilipuk.dto.OrderCreatedEvent;
import by.pilipuk.dto.OrderDto;
import by.pilipuk.dto.OrderWriteDto;
import by.pilipuk.entity.Order;
import by.pilipuk.entity.OrderItem;
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
        uses = {OrderItemMapper.class},
        imports = {UUID.class, LocalDateTime.class, Status.class}
)
public abstract class OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "tableNumber", source = "tableNumber")
    @Mapping(target = "items", source = "items")
    public abstract Order toEntity(OrderWriteDto orderWriteDto);

    @AfterMapping
    protected void getItems(@MappingTarget Order order) {
        if (order.getItems() != null) {
            order.getItems().forEach(item -> item.setOrder(order));
        }
    }

    public abstract OrderDto toDto(Order order);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "orderId", source = "id")
    public abstract OrderCreatedEvent toOrderCreatedEvent(Order order);

    @AfterMapping
    protected void mapItemsToNames(Order order, @MappingTarget OrderCreatedEvent event) {
        if (!CollectionUtils.isEmpty(order.getItems())) {
            List<String> itemNames = order.getItems().stream()
                    .map(OrderItem::getName)
                    .toList();
            event.setItems(itemNames);
        } else {
            event.setItems(java.util.Collections.emptyList());
        }
    }

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "topic", constant = "orders")
    @Mapping(target = "event", source = "jsonEvent")
    @Mapping(target = "kafkaMessageStatus", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    public abstract OutboxEvent toOutboxEvent(Order order, String jsonEvent);
}