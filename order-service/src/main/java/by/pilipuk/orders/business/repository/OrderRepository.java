package by.pilipuk.orders.business.repository;

import by.pilipuk.orders.exception.ApplicationException;
import by.pilipuk.orders.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import static by.pilipuk.orders.exception.ApplicationExceptionCode.NOT_FOUND_BY_ID;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    default Order findByIdOrElseThrow(Long id) {
            return findById(id)
                    .orElseThrow(() -> ApplicationException.create(NOT_FOUND_BY_ID, id));
    }
}