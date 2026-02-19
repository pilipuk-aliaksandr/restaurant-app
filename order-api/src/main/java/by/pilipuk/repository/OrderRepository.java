package by.pilipuk.repository;

import by.pilipuk.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    default Order findByIdOrElseThrow(Long id) {
            return findById(id)
                    .orElseThrow(() -> new RuntimeException("NOT_FOUND_BY_ID: " + id));
    }
}
