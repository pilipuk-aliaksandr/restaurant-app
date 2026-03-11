package by.pilipuk.gateway.business.service;

import by.pilipuk.gateway.dto.UserWriteDto;
import by.pilipuk.gateway.model.entity.User;
import by.pilipuk.gateway.business.mapper.UserMapper;
import by.pilipuk.gateway.business.repository.UserRepository;
import by.pilipuk.gateway.business.repository.UserRoleRepository;
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

    //24 и 25 в маппер
    public void saveUser(UserWriteDto userWriteDto) {
        User user = userMapper.toUser(userWriteDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(userRoleRepository.findById(1L).orElseThrow()); // Creating new admins is only possible by using Liquibase
        userRepository.save(user);
    }

}
