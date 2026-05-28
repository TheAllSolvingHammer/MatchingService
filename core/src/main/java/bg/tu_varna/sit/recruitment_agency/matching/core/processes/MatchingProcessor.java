package bg.tu_varna.sit.recruitment_agency.matching.core.processes;

import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.dto.AiMatchResult;
import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.event.ApplicationSubmittedEvent;
import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.feign.CandidateOutput;
import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.feign.GetCandidateByIDInput;
import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.feign.OpportunityOutput;
import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.feign.RequirementView;
import bg.tu_varna.sit.recruitment_agency.matching.core.feign.OpportunityClient;
import bg.tu_varna.sit.recruitment_agency.matching.core.feign.ProfileClient;
import bg.tu_varna.sit.recruitment_agency.matching.core.services.CvParserService;
import bg.tu_varna.sit.recruitment_agency.matching.core.services.ai.GeminiService;
import bg.tu_varna.sit.recruitment_agency.matching.persistence.entities.MatchResultEntity;
import bg.tu_varna.sit.recruitment_agency.matching.persistence.repositories.MatchResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchingProcessor {

    private final ProfileClient profileClient;
    private final OpportunityClient opportunityClient;
    private final CvParserService cvParserService;
    private final GeminiService geminiService;
    private final MatchResultRepository matchResultRepository;

    @Transactional
    public void executeMatching(ApplicationSubmittedEvent event) {
        log.info("Starting matching process for Application: {}", event.getApplicationId());

        try {
            GetCandidateByIDInput input = GetCandidateByIDInput
                    .builder()
                    .id(event.getCandidateId())
                    .build();

            CandidateOutput candidate = profileClient.getCandidateProfile(input);
            OpportunityOutput opportunity = opportunityClient.getOpportunityById(event.getOpportunityId());

            // 2. Manual Scoring (Tag/Skill intersection)
            double manualScore = calculateManualScore(candidate.getSkillNames(), opportunity.getRequirementsSet());
            log.info("Calculated Manual Score: {}%", manualScore);

            // 3. AI Scoring (CV vs Description)
            String cvText = cvParserService.extractTextFromUrl(event.getCvUrl());
            AiMatchResult aiResult = geminiService.analyzeMatch(cvText, opportunity.getDescription());
            log.info("Calculated AI Score: {}%", aiResult.getScore());

            // 4. Calculate Final Weighted Score (60% Manual, 40% AI)
            double finalScore = (manualScore * 0.6) + (aiResult.getScore() * 0.4);

            // 5. Persist Results
            MatchResultEntity result = MatchResultEntity.builder()
                    .opportunityId(event.getOpportunityId())
                    .candidateId(event.getCandidateId())
                    .manualScore(manualScore)
                    .aiScore(aiResult.getScore())
                    .aiReasoning(aiResult.getReason())
                    .finalScore(finalScore)
                    .calculatedAt(LocalDateTime.now())
                    .build();

            matchResultRepository.save(result);
            log.info("Matching completed successfully. Final Score: {}%", String.format("%.2f", finalScore));

        } catch (Exception e) {
            log.error("Failed to process matching for application {}: {}", event.getApplicationId(), e.getMessage());
        }
    }

    /**
     * Calculates what percentage of the job's required skills the candidate possesses.
     */
    private double calculateManualScore(Set<UUID> candidateSkillIds, Set<RequirementView> jobRequirements) {
        if (jobRequirements == null || jobRequirements.isEmpty()) {
            return 100.0;
        }

        long matchedCount = jobRequirements.stream()
                .filter(req -> candidateSkillIds.contains(req.getSkillId()))
                .count();

        return (double) matchedCount / jobRequirements.size() * 100;
    }
}
