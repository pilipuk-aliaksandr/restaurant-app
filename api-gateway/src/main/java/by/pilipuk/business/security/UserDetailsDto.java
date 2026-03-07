package by.pilipuk.business.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

}
