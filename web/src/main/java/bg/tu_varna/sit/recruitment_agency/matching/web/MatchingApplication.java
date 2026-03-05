package bg.tu_varna.sit.recruitment_agency.matching.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "bg.tu_varna.sit.recruitment_agency.matching.persistence")
@EntityScan(basePackages = "bg.tu_varna.sit.recruitment_agency.matching.persistence")
@EnableFeignClients(basePackages = "bg.tu_varna.sit.recruitment_agency.matching.core")
@EnableDiscoveryClient
public class MatchingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchingApplication.class, args);
    }

}
