package bg.tu_varna.sit.recruitment_agency.matching.core.feign;

import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.feign.OpportunityOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "opportunityservice")
public interface OpportunityClient {

    @GetMapping("/api/v1/opportunities/{id}")
    OpportunityOutput getOpportunityById(@PathVariable("id") UUID id);
}
