package bg.tu_varna.sit.recruitment_agency.matching.web.events;

import bg.tu_varna.sit.recruitment_agency.matching.api.opportunity.models.event.ApplicationSubmittedEvent;
import bg.tu_varna.sit.recruitment_agency.matching.core.processes.MatchingProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationEventConsumer {

    private final MatchingProcessor matchingProcessor;

    @RabbitListener(queues = "${rabbitmq.queue.application-submitted}")
    public void consumeApplicationEvent(ApplicationSubmittedEvent event) {
        log.info("Received application event for Candidate: {} and Opportunity: {}",
                event.getCandidateId(), event.getOpportunityId());

        // This triggers the heavy lifting
        matchingProcessor.executeMatching(event);
    }
}
