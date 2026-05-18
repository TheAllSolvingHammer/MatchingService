package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.matching;

import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchesForJobInput implements OperationInput {
    private UUID opportunityId;
    private int page;
    private int size;
}
