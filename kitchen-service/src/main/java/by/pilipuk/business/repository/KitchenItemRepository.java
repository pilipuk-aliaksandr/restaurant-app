package by.pilipuk.business.repository;

import by.pilipuk.core.exception.ValidationException;
import by.pilipuk.model.entity.KitchenItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import static by.pilipuk.core.exception.ValidationCode.NOT_FOUND_BY_ID;

@Repository
public interface KitchenItemRepository extends JpaRepository<KitchenItem, Long> {

    default KitchenItem findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> ValidationException.create(NOT_FOUND_BY_ID, id));
    }

    Optional<KitchenItem> findFirstByCookedFalseOrderByCreatedAtAsc();
}
