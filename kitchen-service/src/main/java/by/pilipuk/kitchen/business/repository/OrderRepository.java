package by.pilipuk.kitchen.business.repository;

import by.pilipuk.kitchen.core.exception.KitchenException;
import by.pilipuk.kitchen.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import static by.pilipuk.kitchen.core.exception.KitchenExceptionCode.NOT_FOUND_BY_ID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    default Order findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> KitchenException.create(NOT_FOUND_BY_ID, id));
    }
}