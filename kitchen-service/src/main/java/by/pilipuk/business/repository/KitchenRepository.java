package by.pilipuk.business.repository;

import by.pilipuk.core.exception.KitchenException;
import by.pilipuk.model.entity.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import static by.pilipuk.core.exception.KitchenExceptionCode.NOT_FOUND_BY_ID;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long>, JpaSpecificationExecutor<Kitchen> {

    default Kitchen findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> KitchenException.create(NOT_FOUND_BY_ID, id));
    }
}