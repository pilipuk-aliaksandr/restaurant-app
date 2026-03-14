package by.pilipuk.kitchen;

import by.pilipuk.commonCore.CommonCoreBasePackage;
import by.pilipuk.commonKafka.CommonKafkaBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackageClasses = {CommonCoreBasePackage.class, CommonKafkaBasePackage.class, KitchenApiApp.class})
@EnableJpaRepositories(basePackageClasses = {CommonCoreBasePackage.class, CommonKafkaBasePackage.class, KitchenApiApp.class})
@EntityScan(basePackageClasses = {CommonCoreBasePackage.class, CommonKafkaBasePackage.class, KitchenApiApp.class})
@EnableJpaAuditing
@EnableScheduling
@EnableKafka
@EnableCaching
public class KitchenApiApp {
    public static void main(String[] args) {
        SpringApplication.run(KitchenApiApp.class, args);
    }
}