package by.pilipuk.orders;

import by.pilipuk.common.CommonBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackageClasses = {CommonBasePackage.class, OrderApiApp.class})
@EnableJpaRepositories(basePackageClasses = {CommonBasePackage.class, OrderApiApp.class})
@EntityScan(basePackageClasses = {CommonBasePackage.class, OrderApiApp.class})
@EnableJpaAuditing
@EnableKafka
@EnableCaching
public class OrderApiApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApiApp.class, args);
    }
}