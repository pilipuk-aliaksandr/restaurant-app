package by.pilipuk.kitchen.business.mapper;

import by.pilipuk.kitchen.dto.OrderDto;
import by.pilipuk.kitchen.dto.OrderWriteDto;
import by.pilipuk.commonKafka.model.dto.OrderCreatedEvent;
import by.pilipuk.commonKafka.model.dto.OrderReadyEvent;
import by.pilipuk.kitchen.model.entity.Order;
import by.pilipuk.kitchen.model.entity.OrderItem;
import by.pilipuk.commonKafka.model.entity.OutboxEvent;
import by.pilipuk.kitchen.model.entity.Status;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {OrderItemMapper.class},
        imports = {LocalDateTime.class, Status.class}
)
public abstract class OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "status", constant = "ACCEPTED")
    @Mapping(target = "items", source = "items")
    public abstract Order toEntity(OrderWriteDto orderOrderWriteDto);

    @AfterMapping
    protected void getItems(@MappingTarget Order order) {
        if (order.getItems() != null) {
            order.getItems().forEach(item -> item.setOrder(order));
        }
    }

    public abstract OrderDto toDto(Order order);

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "items", ignore = true)
    public abstract Order toEntity(OrderCreatedEvent orderCreatedEvent);

    @AfterMapping
    protected void fillItems(OrderCreatedEvent event, @MappingTarget Order order) {
        if (!CollectionUtils.isEmpty(event.getItems())) {
            List<OrderItem> orderItems = event.getItems().stream()
                    .map(name -> {
                        OrderItem item = new OrderItem();
                        item.setName(name);
                        item.setOrder(order);
                        return item;
                    })
                    .toList();
            order.setItems(orderItems);
        }
    }

    @Mapping(target = "items", ignore = true)
    public abstract OrderReadyEvent toOrderReadyEvent(Order order);

    @AfterMapping
    protected void mapItemsToNames(Order order, @MappingTarget OrderReadyEvent event) {
        if (!CollectionUtils.isEmpty(order.getItems())) {
            List<String> itemNames = order.getItems().stream()
                    .map(OrderItem::getName)
                    .toList();
            event.setItems(itemNames);
        }
    }

    @Mapping(target = "keyOrderId", source = "order.id")
    @Mapping(target = "topic", constant = "ready_orders")
    @Mapping(target = "payload", source = "jsonEvent")
    public abstract OutboxEvent toOutboxEvent(Order order, String jsonEvent);
}