package by.pilipuk.orders.business.repository;

import by.pilipuk.orders.exception.OrderException;
import by.pilipuk.orders.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import static by.pilipuk.orders.exception.OrderExceptionCode.NOT_FOUND_BY_ID;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    default OrderItem findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> OrderException.create(NOT_FOUND_BY_ID, id));
    }
}