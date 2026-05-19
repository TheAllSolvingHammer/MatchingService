package bg.tu_varna.sit.recruitment_agency.matching.web.controllers;

import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.matching.GetMatchesForCandidateInput;
import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.matching.GetMatchesForJobInput;
import bg.tu_varna.sit.recruitment_agency.matching.core.processes.MatchingQueryProcessors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/matching")
@RequiredArgsConstructor
@Tag(name = "Matching Results", description = "Query endpoints to view AI and Manual matching scores.")
public class MatchingController {

    private final MatchingQueryProcessors queryProcessors;

    @Operation(summary = "Get top candidates for a job", description = "Used by Institutions to see who fits their job best.")
    @GetMapping("/opportunity/{opportunityId}")
    public ResponseEntity<?> getBestCandidatesForJob(
            @PathVariable UUID opportunityId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        GetMatchesForJobInput input = GetMatchesForJobInput.builder()
                .opportunityId(opportunityId)
                .page(page)
                .size(size)
                .build();

        return ResponseEntity.ok(queryProcessors.getBestCandidatesForJob(input));
    }

    @Operation(summary = "Get top jobs for a candidate", description = "Used by Candidates to see which jobs they are most qualified for.")
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<?> getBestJobsForCandidate(
            @PathVariable UUID candidateId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        GetMatchesForCandidateInput input = GetMatchesForCandidateInput.builder()
                .candidateId(candidateId)
                .page(page)
                .size(size)
                .build();

        return ResponseEntity.ok(queryProcessors.getBestJobsForCandidate(input));
    }
}