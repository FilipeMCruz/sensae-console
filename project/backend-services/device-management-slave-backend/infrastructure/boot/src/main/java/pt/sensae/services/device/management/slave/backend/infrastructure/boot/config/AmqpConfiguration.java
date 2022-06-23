package pt.sensae.services.device.management.slave.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sensae.services.device.management.slave.backend.application.RoutingKeysProvider;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.sensor.routing.keys.RecordsOptions;

@Configuration
public class AmqpConfiguration {

    private final RoutingKeysProvider provider;

    private final QueueNamingService service;

    public AmqpConfiguration(RoutingKeysProvider provider, QueueNamingService service) {
        this.provider = provider;
        this.service = service;
    }

    @Bean
    public TopicExchange dataTopic() {
        return new TopicExchange(IoTCoreTopic.DATA_EXCHANGE);
    }

    @Bean
    public Queue dataQueue() {
        return QueueBuilder.durable(service.getDataQueueName())
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding dataBinding() {
        var keys = provider.getSensorTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withRecords(RecordsOptions.UNIDENTIFIED_RECORDS)
                .withOwnership(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .withLegitimacyType(DataLegitimacyOptions.UNKNOWN)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(dataQueue()).to(dataTopic()).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public TopicExchange internalTopic() {
        return new TopicExchange(IoTCoreTopic.INTERNAL_EXCHANGE);
    }

    @Bean
    public Queue internalQueue() {
        return QueueBuilder.durable(service.getDeviceManagementQueueName())
                .autoDelete()
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding internalBinding() {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_MANAGEMENT)
                .withContainerType(ContainerTypeOptions.DEVICE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(internalQueue()).to(internalTopic()).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }
}
