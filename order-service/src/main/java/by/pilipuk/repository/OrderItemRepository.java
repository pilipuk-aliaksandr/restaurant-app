package by.pilipuk.repository;

import by.pilipuk.entity.OrderItem;
import by.pilipuk.exception.ValidationException;
import org.springframework.data.jpa.repository.JpaRepository;

import static by.pilipuk.exception.ValidationCode.NOT_FOUND_BY_ID;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    default OrderItem findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> ValidationException.create(NOT_FOUND_BY_ID, id));
    }

}
