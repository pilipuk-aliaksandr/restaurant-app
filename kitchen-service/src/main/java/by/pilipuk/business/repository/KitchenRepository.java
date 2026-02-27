package by.pilipuk.business.repository;

import by.pilipuk.core.exception.ValidationException;
import by.pilipuk.model.entity.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import static by.pilipuk.core.exception.ValidationCode.NOT_FOUND_BY_ID;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long>, JpaSpecificationExecutor<Kitchen> {

    default Kitchen findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> ValidationException.create(NOT_FOUND_BY_ID, id));
    }
}