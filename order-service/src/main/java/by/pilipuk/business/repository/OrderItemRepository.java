package by.pilipuk.business.repository;

import by.pilipuk.model.entity.OrderItem;
import by.pilipuk.core.exception.ValidationException;
import org.springframework.data.jpa.repository.JpaRepository;
import static by.pilipuk.core.exception.ValidationCode.NOT_FOUND_BY_ID;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    default OrderItem findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> ValidationException.create(NOT_FOUND_BY_ID, id));
    }
}