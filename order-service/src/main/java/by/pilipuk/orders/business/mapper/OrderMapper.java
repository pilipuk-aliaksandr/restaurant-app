package by.pilipuk.orders.business.mapper;

import by.pilipuk.common.model.dto.OrderCreatedEvent;
import by.pilipuk.orders.dto.OrderDto;
import by.pilipuk.orders.dto.OrderWriteDto;
import by.pilipuk.orders.model.entity.Order;
import by.pilipuk.orders.model.entity.OrderItem;
import by.pilipuk.common.model.entity.OutboxEvent;
import by.pilipuk.orders.model.entity.Status;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {OrderItemMapper.class},
        imports = {Status.class}
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "topic", constant = "orders")
    @Mapping(target = "keyOrderId", source = "order.id")
    @Mapping(target = "payload", source = "jsonEvent")
    public abstract OutboxEvent toOutboxEvent(Order order, String jsonEvent);
}