package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.matching;

import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.base.OperationOutput;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchOutput implements OperationOutput {
    private UUID matchId;
    private UUID opportunityId;
    private UUID candidateId;
    private Double finalScore;
    private Double manualScore;
    private Double aiScore;
    private String aiReasoning;
    private LocalDateTime calculatedAt;
}
