package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum RequirementImportanceView {
    MANDATORY("Mandatory"),
    PREFERRED("Preferred"),
    OPTIONAL("Optional"),
    UNSPECIFIED("Unspecified");

    @Getter
    private final String value;

    private static final Map<String, RequirementImportanceView> map = new HashMap<>();

    RequirementImportanceView(String value) {
        this.value = value;
    }

    static {
        Arrays.stream(RequirementImportanceView.values())
                .forEach(r -> map.put(r.value, r));
    }

    public static RequirementImportanceView getFromValue(String value) {
        return map.getOrDefault(value, UNSPECIFIED);
    }
}
