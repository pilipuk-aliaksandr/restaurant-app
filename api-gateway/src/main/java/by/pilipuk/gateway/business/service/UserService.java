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

    public void saveUser(UserWriteDto userWriteDto) {
        var user = userMapper.toUser(userWriteDto);
        userRepository.save(user);
    }
}