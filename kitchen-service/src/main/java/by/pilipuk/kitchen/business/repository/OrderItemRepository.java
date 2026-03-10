package by.pilipuk.kitchen.business.repository;

import by.pilipuk.kitchen.core.exception.KitchenException;
import by.pilipuk.kitchen.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import static by.pilipuk.kitchen.core.exception.KitchenExceptionCode.NOT_FOUND_BY_ID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    default OrderItem findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> KitchenException.create(NOT_FOUND_BY_ID, id));
    }

    Optional<OrderItem> findFirstByCookedFalseOrderByCreatedAtAsc();
}
