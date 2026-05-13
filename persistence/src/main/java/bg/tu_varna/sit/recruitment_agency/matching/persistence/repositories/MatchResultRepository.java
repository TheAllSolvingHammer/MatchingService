package bg.tu_varna.sit.recruitment_agency.matching.persistence.repositories;

import bg.tu_varna.sit.recruitment_agency.matching.persistence.entities.MatchResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface MatchResultRepository extends JpaRepository<MatchResultEntity, UUID> {
}
