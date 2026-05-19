package bg.tu_varna.sit.recruitment_agency.matching.core.services;


import bg.tu_varna.sit.recruitment_agency.matching.core.base.ScoringStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManualScoringService implements ScoringStrategy {

    @Override
    public double calculate(Object source, Object target) {
        return 0;
    }
}
