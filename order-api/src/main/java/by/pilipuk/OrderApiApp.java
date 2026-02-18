package by.pilipuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OrderApiApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApiApp.class, args);
    }
}