package by.pilipuk.gateway.business.service;

import by.pilipuk.gateway.model.dto.UserDetailsDto;
import by.pilipuk.gateway.business.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsernameIgnoreCaseOrElseThrow(username);

        var authorities = List.of(new SimpleGrantedAuthority(user.getUserRole().getRole().name()));

        return new UserDetailsDto(user.getId(), user.getUsername(), user.getPassword(), authorities);
    }
}