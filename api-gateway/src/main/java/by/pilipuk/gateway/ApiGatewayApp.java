package by.pilipuk.gateway;

import by.pilipuk.common.CommonBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackageClasses = {CommonBasePackage.class, ApiGatewayApp.class})
@EnableJpaRepositories(basePackageClasses = {CommonBasePackage.class, ApiGatewayApp.class})
@EntityScan(basePackageClasses = {CommonBasePackage.class, ApiGatewayApp.class})
@EnableJpaAuditing
public class ApiGatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApp.class, args);
    }
}