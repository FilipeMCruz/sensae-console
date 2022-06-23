package pt.sensae.services.data.processor.slave.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.InfoTypeOptions;
import pt.sensae.services.data.processor.slave.backend.application.RoutingKeysProvider;
import pt.sensae.services.data.processor.slave.backend.infrastructure.endpoint.amqp.ingress.controller.SensorDataConsumer;

import static pt.sensae.services.data.processor.slave.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE;
import static pt.sensae.services.data.processor.slave.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE;

@Configuration
public class AmqpConfiguration {

    private final RoutingKeysProvider provider;

    private final QueueNamingService service;

    public AmqpConfiguration(RoutingKeysProvider provider, QueueNamingService service) {
        this.provider = provider;
        this.service = service;
    }

    @Bean
    public Queue internalQueue() {
        return QueueBuilder.durable(service.getDataProcessorQueueName())
                .autoDelete()
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public TopicExchange internalTopic() {
        return new TopicExchange(IoTCoreTopic.INTERNAL_EXCHANGE);
    }

    @Bean
    Binding bindingInternal() {
        var decoded = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DATA_PROCESSOR)
                .withContainerType(ContainerTypeOptions.DATA_PROCESSOR)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (decoded.isPresent()) {
            return BindingBuilder.bind(internalQueue()).to(internalTopic()).with(decoded.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(IoTCoreTopic.DATA_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(service.getDataQueueName())
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding binding() {
        var decoded = provider.getSensorTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.DECODED)
                .missingAsAny();
        if (decoded.isPresent()) {
            return BindingBuilder.bind(queue()).to(topic()).with(decoded.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }
}
