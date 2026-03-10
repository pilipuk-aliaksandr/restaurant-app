package by.pilipuk.environment.data.entityCreation;

import by.pilipuk.gateway.business.repository.UserRepository;
import by.pilipuk.gateway.business.repository.UserRoleRepository;
import by.pilipuk.gateway.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserCreator {

    private final PasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public User createUser() {
        var user = new User();
        user.setId(1L);
        user.setUsername("Alex");
        user.setUserRole(userRoleRepository.findByIdOrElseThrow(1L));
        user.setPassword(passwordEncoder.encode("Vishn"));
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        return user;
    }

    public User saveUser(User user) {
        user.setId(null);

        return userRepository.save(user);
    }
}