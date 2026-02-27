package by.pilipuk.business.repository;

import by.pilipuk.model.entity.Order;
import by.pilipuk.core.exception.ValidationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import static by.pilipuk.core.exception.ValidationCode.NOT_FOUND_BY_ID;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    default Order findByIdOrElseThrow(Long id) {
            return findById(id)
                    .orElseThrow(() -> ValidationException.create(NOT_FOUND_BY_ID, id));
    }
}