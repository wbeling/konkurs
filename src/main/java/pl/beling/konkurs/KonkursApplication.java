package pl.beling.konkurs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Konkurs Application class
 */
@SpringBootApplication
@ComponentScan(basePackages = {"pl.beling.konkurs", "pl.beling.konkurs.api", "pl.beling.konkurs.service"})
public class KonkursApplication {
    public static void main(String[] args) {
        new SpringApplication(KonkursApplication.class).run(args);
    }

}
