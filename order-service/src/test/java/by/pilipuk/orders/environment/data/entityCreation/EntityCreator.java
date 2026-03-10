package by.pilipuk.orders.environment.data.entityCreation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityCreator {

    public final OrderCreator orderCreator;
    public final OrderItemsCreator orderItemsCreator;

}