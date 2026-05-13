package bg.tu_varna.sit.recruitment_agency.matching.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "match_results")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID opportunityId;
    private UUID candidateId;
    private Double manualScore;
    private Double aiScore;
    private Double finalScore;

    @Column(columnDefinition = "TEXT")
    private String aiReasoning;

    private LocalDateTime calculatedAt;
}
