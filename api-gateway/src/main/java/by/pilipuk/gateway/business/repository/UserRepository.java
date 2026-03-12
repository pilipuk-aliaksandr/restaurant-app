package by.pilipuk.gateway.business.repository;

import by.pilipuk.gateway.model.entity.User;
import by.pilipuk.gateway.core.exception.ApplicationException;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import static by.pilipuk.gateway.core.exception.ApplicationExceptionCode.NOT_FOUND_BY_ID;
import static by.pilipuk.gateway.core.exception.ApplicationExceptionCode.NOT_FOUND_BY_USERNAME;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameIgnoreCase(String username);

    default User findByUsernameIgnoreCaseOrElseThrow(String username) {
        return findByUsernameIgnoreCase(username)
                .orElseThrow(() -> ApplicationException.create(NOT_FOUND_BY_USERNAME, username));
    }

    default User findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> ApplicationException.create(NOT_FOUND_BY_ID, id));
    }
}