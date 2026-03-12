package by.pilipuk.gateway.business.service;

import by.pilipuk.gateway.business.mapper.UserDetailsMapper;
import by.pilipuk.gateway.model.entity.User;
import by.pilipuk.gateway.business.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository
                .findByUsernameIgnoreCaseOrElseThrow(username);

        return userDetailsMapper.toUserDetailsDto(user);
    }
}