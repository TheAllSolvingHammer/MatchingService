package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.feign;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCandidateByIDInput {
    private UUID id;
}
