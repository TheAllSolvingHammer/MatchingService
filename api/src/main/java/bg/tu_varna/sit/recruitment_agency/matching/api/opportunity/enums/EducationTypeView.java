package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum EducationTypeView {
    UNEDUCATED("Uneducated"),
    PRIMARY("Primary"),
    HIGH_SCHOOL("High School"),
    PROFESSIONAL_BACHELOR("Professional Bachelor"),
    BACHELOR("Bachelor"),
    MASTER("Master"),
    PHD("Doctor"),
    PROFESSOR("Professor");

    @JsonValue
    private final String value;
    private static final Map<String, EducationTypeView> map = new HashMap<>();

    EducationTypeView(String value) {
        this.value = value;
    }

    static {
        Arrays.stream(EducationTypeView.values())
                .forEach(e ->
                        map.put(e.value, e));
    }

    @JsonCreator
    public static EducationTypeView getFromValue(String value) {
        return map.getOrDefault(value, UNEDUCATED);
    }
}
