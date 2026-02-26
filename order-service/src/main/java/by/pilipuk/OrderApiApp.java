package by.pilipuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

/* TODO
Структура модулей
    by.pilipuk
        entrypoint
            controller
            listener
        business
            repository
            service
            mapper
        core
            config
            util
            exception
        model
            dto
            entity
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableKafka
public class OrderApiApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApiApp.class, args);
    }
}