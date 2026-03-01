package by.pilipuk.business.repository;

import by.pilipuk.core.exception.KitchenException;
import by.pilipuk.model.entity.KitchenItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import static by.pilipuk.core.exception.KitchenExceptionCode.NOT_FOUND_BY_ID;

@Repository
public interface KitchenItemRepository extends JpaRepository<KitchenItem, Long> {

    default KitchenItem findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> KitchenException.create(NOT_FOUND_BY_ID, id));
    }

    Optional<KitchenItem> findFirstByCookedFalseOrderByCreatedAtAsc();
}
