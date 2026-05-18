package bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.base;


import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.exceptions.ErrorProcessor;
import io.vavr.control.Either;

public interface OperationRequest<T extends OperationOutput, E extends OperationInput> {
    Either<ErrorProcessor, T> process(E operationInput);
}
