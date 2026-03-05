package by.pilipuk.business.service;

import by.pilipuk.dto.UserWriteDto;
import by.pilipuk.model.entity.User;
import by.pilipuk.business.mapper.UserMapper;
import by.pilipuk.business.repository.UserRepository;
import by.pilipuk.business.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;

    public void saveUser(UserWriteDto userWriteDto) {
        User user = userMapper.toUser(userWriteDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(userRoleRepository.findById(1L).orElseThrow()); // Creating new admins is only possible by using Liquibase
        userRepository.save(user);
    }

}
