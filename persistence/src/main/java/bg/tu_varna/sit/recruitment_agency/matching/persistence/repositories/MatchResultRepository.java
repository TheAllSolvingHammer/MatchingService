package bg.tu_varna.sit.recruitment_agency.matching.persistence.repositories;

import bg.tu_varna.sit.recruitment_agency.matching.persistence.entities.MatchResultEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface MatchResultRepository extends JpaRepository<MatchResultEntity, UUID> {
    Page<MatchResultEntity> findAllByOpportunityIdOrderByFinalScoreDesc(UUID opportunityId, Pageable pageable);

    // Fetches all jobs a specific candidate applied for, ordered by highest final score
    Page<MatchResultEntity> findAllByCandidateIdOrderByFinalScoreDesc(UUID candidateId, Pageable pageable);

    // Prevents duplicate matching calculations for the same application
    boolean existsByOpportunityIdAndCandidateId(UUID opportunityId, UUID candidateId);
}
