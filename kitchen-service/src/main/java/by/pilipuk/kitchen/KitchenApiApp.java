package by.pilipuk.kitchen;

import by.pilipuk.common.CommonBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackageClasses = {CommonBasePackage.class, KitchenApiApp.class})
@EnableJpaRepositories(basePackageClasses = {CommonBasePackage.class, KitchenApiApp.class})
@EntityScan(basePackageClasses = {CommonBasePackage.class, KitchenApiApp.class})
@EnableJpaAuditing
@EnableScheduling
@EnableKafka
@EnableCaching
public class KitchenApiApp {
    public static void main(String[] args) {
        SpringApplication.run(KitchenApiApp.class, args);
    }
}