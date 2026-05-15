package bg.tu_varna.sit.recruitment_agency.matching.web.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfig {
    // Matches the queue name defined in your application.properties
    @Value("${rabbitmq.queue.application-submitted:application-submitted-queue}")
    private String queueName;

    /**
     * Ensures the queue exists in RabbitMQ on startup.
     */
    @Bean
    public Queue applicationSubmittedQueue() {
        return new Queue(queueName, true); // true = durable (survives broker restart)
    }

    /**
     * Tells Spring to automatically serialize/deserialize RabbitMQ messages to JSON
     * instead of relying on default Java serialization.
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
