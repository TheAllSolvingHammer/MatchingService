package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.feign;

import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.enums.CandidateTypeView;
import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.enums.EducationTypeView;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateOutput {
    private UUID id;
    private String username;
    private String fullName;
    private String displayName;
    private CandidateTypeView candidateType;
    private EducationTypeView educationType;
    private Set<UUID> skillNames;
}
