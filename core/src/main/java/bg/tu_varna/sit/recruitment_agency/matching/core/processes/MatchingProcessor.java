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
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchingProcessor {
    public @Nullable Object getTopMatchesForJob(UUID opportunityId) {
        return null;
    }

    public @Nullable Object getTopMatchesForCandidate(UUID candidateId) {
        return null;
    }

    private final ProfileClient profileClient;
    private final OpportunityClient opportunityClient;
    private final CvParserService cvParserService;
    private final GeminiService geminiService;
    private final MatchResultRepository matchResultRepository;

    @Transactional
    public void executeMatching(ApplicationSubmittedEvent event) {
        log.info("Starting matching process for Application: {}", event.getApplicationId());

        // 1. Fetch data from other services via Feign
        GetCandidateByIDInput input = GetCandidateByIDInput
                .builder()
                .id(event.getApplicationId())
                .build();
        CandidateOutput candidate = profileClient.getCandidateProfile(input);
        OpportunityOutput opportunity = opportunityClient.getOpportunityById(event.getOpportunityId());

        // 2. Manual Scoring (Tag intersection)
        double manualScore = calculateManualScore(candidate.getSkillNames(), opportunity.getRequirementsSet());

        // 3. AI Scoring (CV vs Description)
        String cvText = cvParserService.extractTextFromUrl(event.getCvUrl());
        AiMatchResult aiResult = geminiService.analyzeMatch(cvText, opportunity.getDescription());

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
        log.info("Matching completed. Final Score: {}%", String.format("%.2f", finalScore));
    }

    private double calculateManualScore(Set<UUID> candidateSkills, Set<RequirementView> jobRequirements) {
        if (jobRequirements.isEmpty()) return 100.0;

        long matchedCount = jobRequirements.stream()
                .filter(req -> candidateSkills.contains(req.getSkillId()))
                .count();

        return (double) matchedCount / jobRequirements.size() * 100;
    }
}
