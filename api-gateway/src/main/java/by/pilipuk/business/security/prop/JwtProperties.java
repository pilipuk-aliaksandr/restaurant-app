package by.pilipuk.business.security.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.security.jwt")
public class JwtProperties {

    private String secret;

    private Long access;

    private Long refresh;

}