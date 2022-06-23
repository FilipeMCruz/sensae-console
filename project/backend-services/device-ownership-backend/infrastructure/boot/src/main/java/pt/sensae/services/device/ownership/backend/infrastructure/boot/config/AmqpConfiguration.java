package pt.sensae.services.device.ownership.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.sensor.routing.keys.RecordsOptions;
import pt.sensae.services.device.ownership.backend.application.RoutingKeysProvider;

@Configuration
public class AmqpConfiguration {

    private final RoutingKeysProvider provider;

    private final QueueNamingService queueNamingService;

    public AmqpConfiguration(RoutingKeysProvider provider, QueueNamingService queueNamingService) {
        this.provider = provider;
        this.queueNamingService = queueNamingService;
    }

    @Bean
    public Queue slaveQueue() {
        return QueueBuilder.durable(queueNamingService.getDeviceOwnershipQueueName())
                .autoDelete()
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public TopicExchange internalTopic() {
        return new TopicExchange(IoTCoreTopic.INTERNAL_EXCHANGE);
    }

    @Bean
    Binding bindingMaster() {
        var decoded = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_IDENTITY)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (decoded.isPresent()) {
            return BindingBuilder.bind(slaveQueue()).to(internalTopic()).with(decoded.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public TopicExchange dataTopic() {
        return new TopicExchange(IoTCoreTopic.DATA_EXCHANGE);
    }

    @Bean
    public Queue dataQueue() {
        return QueueBuilder.durable(queueNamingService.getDataQueueName())
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding dataBinding() {
        var keys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withOwnership(OwnershipOptions.UNIDENTIFIED_DOMAIN_OWNERSHIP)
                .withRecords(RecordsOptions.UNIDENTIFIED_RECORDS)
                .withLegitimacyType(DataLegitimacyOptions.UNKNOWN)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(dataQueue()).to(dataTopic()).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public TopicExchange alertTopic() {
        return new TopicExchange(IoTCoreTopic.ALERT_EXCHANGE);
    }

    @Bean
    public Queue alertQueue() {
        return QueueBuilder.durable(queueNamingService.getAlertQueueName())
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding alertBinding() {
        var keys = provider.getAlertTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withOwnershipType(OwnershipOptions.UNIDENTIFIED_DOMAIN_OWNERSHIP)
                .withContainerType(ContainerTypeOptions.ALERT_DISPATCHER)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(alertQueue()).to(alertTopic()).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }
}
