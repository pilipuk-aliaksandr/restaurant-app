package by.pilipuk.repository;

import by.pilipuk.entity.KitchenOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenOrderRepository extends JpaRepository<KitchenOrder, Long>, JpaSpecificationExecutor<KitchenOrder> {

    default KitchenOrder findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_BY_ID" + id));
    }
}
