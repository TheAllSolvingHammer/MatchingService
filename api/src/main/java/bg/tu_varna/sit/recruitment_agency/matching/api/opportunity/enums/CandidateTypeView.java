package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum CandidateTypeView {
    ACADEMIC_STAFF("Academic Staff"),
    PROFESSIONAL("Professional"),
    ACADEMIC("Academic"),
    INSTITUTIONAL("Institutional"),
    UNKNOWN("Unknown");

    @JsonValue
    private final String value;
    private static final Map<String, CandidateTypeView> map = new HashMap<>();

    CandidateTypeView(String value) {
        this.value = value;
    }

    static {
        Arrays.stream(CandidateTypeView.values())
                .forEach(candidateType ->
                        map.put(candidateType.value, candidateType));
    }

    @JsonCreator
    public static CandidateTypeView getFromValue(String value) {
        return map.getOrDefault(value,UNKNOWN);
    }

}
