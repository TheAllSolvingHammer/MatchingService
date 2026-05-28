package bg.tu_varna.sit.recruitment_agency.matching.core.feign;

import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.feign.CandidateOutput;
import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.feign.GetCandidateByIDInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "profile-service")
public interface ProfileClient {
    @GetMapping("/api/v1/profiles/get_candidate")
    CandidateOutput getCandidateProfile(@SpringQueryMap GetCandidateByIDInput input);

}
