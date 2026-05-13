package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.feign;


import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityOutput {
    private UUID id;
    private String title;
    private String institutionName;
    private String location;
    private String status;
    private String message;
    private String description;
    private Set<RequirementView> requirementsSet;
}
