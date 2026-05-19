package bg.tu_varna.sit.recruitment_agency.matching.core.processes;

import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.matching.GetMatchesForCandidateInput;
import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.matching.GetMatchesForJobInput;
import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.matching.MatchOutput;
import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.matching.PaginatedMatchOutput;
import bg.tu_varna.sit.recruitment_agency.matching.persistence.entities.MatchResultEntity;
import bg.tu_varna.sit.recruitment_agency.matching.persistence.repositories.MatchResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchingQueryProcessors {

    private final MatchResultRepository matchResultRepository;

    public PaginatedMatchOutput getBestCandidatesForJob(GetMatchesForJobInput input) {
        log.info("Fetching top candidates for job: {}", input.getOpportunityId());

        Page<MatchResultEntity> page = matchResultRepository.findAllByOpportunityIdOrderByFinalScoreDesc(
                input.getOpportunityId(),
                PageRequest.of(input.getPage(), input.getSize())
        );

        return mapToPaginatedOutput(page);
    }

    public PaginatedMatchOutput getBestJobsForCandidate(GetMatchesForCandidateInput input) {
        log.info("Fetching top jobs for candidate: {}", input.getCandidateId());

        Page<MatchResultEntity> page = matchResultRepository.findAllByCandidateIdOrderByFinalScoreDesc(
                input.getCandidateId(),
                PageRequest.of(input.getPage(), input.getSize())
        );

        return mapToPaginatedOutput(page);
    }

    private PaginatedMatchOutput mapToPaginatedOutput(Page<MatchResultEntity> page) {
        List<MatchOutput> content = page.getContent().stream()
                .map(entity -> MatchOutput.builder()
                        .matchId(entity.getId())
                        .opportunityId(entity.getOpportunityId())
                        .candidateId(entity.getCandidateId())
                        .finalScore(entity.getFinalScore())
                        .manualScore(entity.getManualScore())
                        .aiScore(entity.getAiScore())
                        .aiReasoning(entity.getAiReasoning())
                        .calculatedAt(entity.getCalculatedAt())
                        .build())
                .collect(Collectors.toList());

        return PaginatedMatchOutput.builder()
                .content(content)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .currentPage(page.getNumber())
                .isLast(page.isLast())
                .build();
    }
}
