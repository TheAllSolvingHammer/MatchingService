package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.feign;


import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.enums.RequirementImportanceView;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementView {
    private UUID skillId;
    private RequirementImportanceView importance;
}
