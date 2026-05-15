package bg.tu_varna.sit.recruitment_agency.matching.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "bg.tu_varna.sit.recruitment_agency.matching.core",
        "bg.tu_varna.sit.recruitment_agency.matching.web"
})
@EnableJpaRepositories(basePackages = "bg.tu_varna.sit.recruitment_agency.matching.persistence")
@EntityScan(basePackages = "bg.tu_varna.sit.recruitment_agency.matching.persistence")
@EnableFeignClients(basePackages = "bg.tu_varna.sit.recruitment_agency.matching.core")
@EnableDiscoveryClient
public class MatchingApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MatchingApplication.class);

        // CRITICAL FIX: Explicitly force Spring Boot to start as a standard Servlet web application.
        // This prevents crashes if Spring Cloud dependencies accidentally pull in WebFlux.
        app.setWebApplicationType(WebApplicationType.SERVLET);

        app.run(args);
    }

}
