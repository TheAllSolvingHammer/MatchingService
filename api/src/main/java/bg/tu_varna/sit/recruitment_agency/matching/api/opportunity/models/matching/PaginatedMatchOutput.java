package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.matching;

import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedMatchOutput implements OperationOutput {
    private List<MatchOutput> content;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private boolean isLast;
}
