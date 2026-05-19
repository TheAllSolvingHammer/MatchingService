package bg.tu_varna.sit.recruitment_agency.matching.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = {
        "bg.tu_varna.sit.recruitment_agency.matching.core",
        "bg.tu_varna.sit.recruitment_agency.matching.web"
})
@EnableJpaRepositories(basePackages = "bg.tu_varna.sit.recruitment_agency.matching.persistence")
@EntityScan(basePackages = "bg.tu_varna.sit.recruitment_agency.matching.persistence")
@EnableFeignClients(basePackages = "bg.tu_varna.sit.recruitment_agency.matching.core")
@EnableDiscoveryClient
@SpringBootApplication
public class MatchingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchingApplication.class, args);
    }

}
