package by.pilipuk.business.repository;

import by.pilipuk.exception.OrderException;
import by.pilipuk.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import static by.pilipuk.exception.OrderExceptionCode.NOT_FOUND_BY_ID;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    default OrderItem findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> OrderException.create(NOT_FOUND_BY_ID, id));
    }
}