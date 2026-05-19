package bg.tu_varna.sit.recruitment_agency.matching.core.base;

public interface ScoringStrategy<T,E> {
    double calculate(T source, E target);
}
