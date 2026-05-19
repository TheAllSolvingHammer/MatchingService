package bg.tu_varna.sit.recruitment_agency.matching.core.services.ai;

import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.dto.AiMatchResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeminiService {
    private final ObjectMapper objectMapper;
    //todo add env variable here
    private final String apiKey = ""; // Provided by environment
    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-preview-09-2025:generateContent?key=";
    private final RestClient restClient = RestClient.create();

    public AiMatchResult analyzeMatch(String cvText, String jobDescription) {
        String prompt = String.format(
                "Act as a technical recruiter. Compare the CV and Job Description.\n\n" +
                        "CV:\n%s\n\nJob Description:\n%s\n\n" +
                        "Provide a match score (0-100) and 1-sentence reason. " +
                        "JSON format: {\"score\": number, \"reason\": \"string\"}",
                cvText, jobDescription
        );

        try {
            Map<String, Object> response = restClient.post()
                    .uri(GEMINI_URL + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))),
                            "generationConfig", Map.of("responseMimeType", "application/json")))
                    .retrieve()
                    .body(Map.class);

            // Safer navigation of the response map
            String jsonOutput = extractText(response);
            return objectMapper.readValue(jsonOutput, AiMatchResult.class);

        } catch (Exception e) {
            log.error("Gemini analysis failed: {}", e.getMessage());
            return AiMatchResult.builder().score(0.0).reason("AI Analysis Unavailable").build();
        }
    }

    private String extractText(Map<String, Object> response) {
        try {
            List<?> candidates = (List<?>) response.get("candidates");
            Map<?, ?> firstCandidate = (Map<?, ?>) candidates.get(0);
            Map<?, ?> content = (Map<?, ?>) firstCandidate.get("content");
            List<?> parts = (List<?>) content.get("parts");
            Map<?, ?> firstPart = (Map<?, ?>) parts.get(0);
            return (String) firstPart.get("text");
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate Gemini response map structure", e);
        }
    }
}
