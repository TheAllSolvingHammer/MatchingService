package bg.tu_varna.sit.recruitment_agency.matching.web.controllers;

import bg.tu_varna.sit.recruitment_agency.matching.core.processes.MatchingProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/matching")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingProcessor matchingProcessor;

    @GetMapping("/opportunity/{opportunityId}")
    public ResponseEntity<?> getBestCandidatesForJob(@PathVariable UUID opportunityId) {
        // Returns candidates sorted by finalScore
        return ResponseEntity.ok(matchingProcessor.getTopMatchesForJob(opportunityId));
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<?> getBestJobsForCandidate(@PathVariable UUID candidateId) {
        // Returns jobs sorted by finalScore
        return ResponseEntity.ok(matchingProcessor.getTopMatchesForCandidate(candidateId));
    }
}