package by.pilipuk.gateway;

import by.pilipuk.commonCore.CommonCoreBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackageClasses = {CommonCoreBasePackage.class, ApiGatewayApp.class})
@EnableJpaRepositories(basePackageClasses = {CommonCoreBasePackage.class, ApiGatewayApp.class})
@EntityScan(basePackageClasses = {CommonCoreBasePackage.class, ApiGatewayApp.class})
@EnableJpaAuditing
public class ApiGatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApp.class, args);
    }
}