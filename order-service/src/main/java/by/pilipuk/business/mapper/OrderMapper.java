package by.pilipuk.business.mapper;

import by.pilipuk.model.dto.OrderCreatedEvent;
import by.pilipuk.dto.OrderDto;
import by.pilipuk.dto.OrderWriteDto;
import by.pilipuk.model.entity.Order;
import by.pilipuk.model.entity.OrderItem;
import by.pilipuk.model.entity.OutboxEvent;
import by.pilipuk.model.entity.Status;
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

    //toDO зачем id - UUID?
    //UUID мне сказала делать GPT
    /*
    в аутбокс таблице достаточно оставить:
        id
        event_type TEXT - (ORDER_CREATED, ORDER_READY)
        key - то что пойдёт как ключ при отправке в топик
        message - payload
        active
        created_at
        updated_at

    всё остальное как будто бы мусор
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "topic", constant = "orders")
    @Mapping(target = "keyOrderId", source = "order.id")
    @Mapping(target = "payload", source = "jsonEvent")
    public abstract OutboxEvent toOutboxEvent(Order order, String jsonEvent);
}