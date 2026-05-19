package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.event;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationSubmittedEvent {
    private UUID applicationId;
    private UUID candidateId;
    private UUID opportunityId;
    private String cvUrl;
}
