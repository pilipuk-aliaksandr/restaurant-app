package by.pilipuk.orders;

import by.pilipuk.commonCore.CommonCoreBasePackage;
import by.pilipuk.commonKafka.CommonKafkaBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackageClasses = {CommonCoreBasePackage.class, CommonKafkaBasePackage.class, OrderApiApp.class})
@EnableJpaRepositories(basePackageClasses = {CommonCoreBasePackage.class, CommonKafkaBasePackage.class, OrderApiApp.class})
@EntityScan(basePackageClasses = {CommonCoreBasePackage.class, CommonKafkaBasePackage.class, OrderApiApp.class})
@EnableJpaAuditing
@EnableKafka
@EnableCaching
public class OrderApiApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApiApp.class, args);
    }
}