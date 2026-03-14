package by.pilipuk.orders.business.repository;

import by.pilipuk.orders.exception.ApplicationException;
import by.pilipuk.orders.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import static by.pilipuk.orders.exception.ApplicationExceptionCode.NOT_FOUND_BY_ID;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    default OrderItem findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> ApplicationException.create(NOT_FOUND_BY_ID, id));
    }
}