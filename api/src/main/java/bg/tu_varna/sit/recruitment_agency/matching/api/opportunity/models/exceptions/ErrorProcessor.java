package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ErrorProcessor {
    private String message;
    private HttpStatus httpStatus;
    private Integer statusCode;
}
