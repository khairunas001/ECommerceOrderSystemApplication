package e_commerce_order_system.e_commerce_order_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ECommerceOrderSystemApplication {

    public static void main(String[] args) {
        // Set JVM default timezone ke UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        SpringApplication.run(ECommerceOrderSystemApplication.class, args);
    }

}
