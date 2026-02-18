package by.pilipuk.repository;

import by.pilipuk.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    default Order findByIdOrElseThrow(Long id) {
        try {
            return findById(id)
                    .orElseThrow(() -> new Exception("NOT_FOUND_BY_ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
