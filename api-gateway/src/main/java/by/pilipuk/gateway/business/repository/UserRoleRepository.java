package by.pilipuk.gateway.business.repository;

import by.pilipuk.gateway.model.entity.UserRole;
import by.pilipuk.gateway.core.exception.ApplicationException;
import org.springframework.data.jpa.repository.JpaRepository;
import static by.pilipuk.gateway.core.exception.ApplicationExceptionCode.NOT_FOUND_BY_ID;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    default UserRole findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> ApplicationException.create(NOT_FOUND_BY_ID, id));
    }
}