package bg.tu_varna.sit.recruitment_agency.matching.core.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Service
@Slf4j
public class CvParserService {
    private final Tika tika = new Tika();

    /**
     * Extracts raw text from a given URL (HTTP/HTTPS or File protocol)
     * using Apache Tika.
     *
     * @param fileUrl The remote or local URL of the CV (PDF, DOCX, etc.)
     * @return Cleaned, extracted text from the document
     */
    public String extractTextFromUrl(String fileUrl) {
        log.info("Attempting to extract text from CV at: {}", fileUrl);

        try {
            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();

            // Add a User-Agent just in case the file is hosted on a server
            // that blocks default Java HTTP clients.
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            try (InputStream stream = connection.getInputStream()) {
                String rawText = tika.parseToString(stream);
                String cleanedText = cleanExtractedText(rawText);

                log.info("Successfully extracted {} characters from CV.", cleanedText.length());
                return cleanedText;
            }
        } catch (Exception e) {
            log.error("Failed to parse CV at {}. Reason: {}", fileUrl, e.getMessage());
            // You can either throw an exception or return an empty string depending
            // on whether you want the AI scoring to fail completely or just score 0.
            throw new RuntimeException("Could not extract text from CV document", e);
        }
    }

    /**
     * Helper method to clean up the extracted text.
     * Removes excessive empty lines, tabs, and unprintable characters
     * to save tokens and improve AI comprehension.
     */
    private String cleanExtractedText(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }

        return text
                // Replace 2 or more consecutive newlines with a single newline
                .replaceAll("[\\r\\n]{2,}", "\n")
                // Replace 2 or more consecutive spaces/tabs with a single space
                .replaceAll("[ \\t]{2,}", " ")
                // Remove leading/trailing whitespaces
                .trim();
    }
}
