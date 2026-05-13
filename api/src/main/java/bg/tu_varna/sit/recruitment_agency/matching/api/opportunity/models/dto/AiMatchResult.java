package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiMatchResult {
    /**
     * The percentage score (0-100) calculated by the AI based on semantic matching.
     */
    private Double score;

    /**
     * A human-readable explanation of why the AI assigned this specific score.
     */
    private String reason;
}
