package by.pilipuk.repository;

import by.pilipuk.entity.Order;
import by.pilipuk.exception.ValidationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import static by.pilipuk.exception.ValidationCode.NOT_FOUND_BY_ID;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    default Order findByIdOrElseThrow(Long id) {
            return findById(id)
                    .orElseThrow(() -> ValidationException.create(NOT_FOUND_BY_ID, id));
    }
}
