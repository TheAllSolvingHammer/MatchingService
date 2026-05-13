package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationSubmittedEvent {
    private UUID applicationId;
    private UUID candidateId;
    private UUID opportunityId;
    private String cvUrl;
}
