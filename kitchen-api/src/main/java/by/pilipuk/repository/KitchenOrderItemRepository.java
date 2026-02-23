package by.pilipuk.repository;

import by.pilipuk.entity.KitchenOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KitchenOrderItemRepository extends JpaRepository<KitchenOrderItem, Long> {

    default KitchenOrderItem findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_BY_ID" + id));
    }

    Optional<KitchenOrderItem> findFirstByCookedFalseOrderByCreatedAtAsc();
}
