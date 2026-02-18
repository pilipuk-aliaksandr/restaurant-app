package by.pilipuk.mapper;

import by.pilipuk.dto.OrderDto;
import by.pilipuk.entity.Order;
import lombok.Setter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        uses = {OrderItemMapper.class}
)
public abstract class OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "tableNumber", source = "tableNumber")
    @Mapping(target = "items", source = "items")
    public abstract Order toEntity(OrderDto orderDto);

    @AfterMapping
    protected void getItems(@MappingTarget Order order) {
        if (order.getItems() != null) {
            order.getItems().forEach(item -> item.setOrder(order));
        }
    }
}
