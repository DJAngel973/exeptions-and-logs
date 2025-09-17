package co.edu.JdA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the Spring Boot application.
 * <p>
 * The {@code @SpringBootApplication} annotation is a shortcut for {@code @Configuration},
 * {@code @EnableAutoConfiguration}, and {@code @ComponentScan} with their default attributes.
 * Its sole responsibility is to bootstrap the Spring application.
 * </p>
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        // La responsabilidad de la clase Main es solo arrancar la aplicación Spring.
        SpringApplication.run(Main.class, args);
    }
}