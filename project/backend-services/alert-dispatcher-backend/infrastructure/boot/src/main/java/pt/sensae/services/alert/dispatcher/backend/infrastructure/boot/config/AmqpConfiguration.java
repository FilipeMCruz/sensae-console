package pt.sensae.services.alert.dispatcher.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sensae.services.alert.dispatcher.backend.application.RoutingKeysProvider;
import pt.sensae.services.alert.dispatcher.backend.infrastructure.endpoint.amqp.ingress.controller.SensorDataConsumer;
import pt.sensae.services.alert.dispatcher.backend.infrastructure.endpoint.amqp.internal.controller.RuleScenarioNotificationConsumer;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.sensor.routing.keys.RecordsOptions;

import static pt.sensae.services.alert.dispatcher.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE;
import static pt.sensae.services.alert.dispatcher.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE;

@Configuration
public class AmqpConfiguration {
    
    public static final String RULE_MANAGEMENT_QUEUE = "internal.rule.management.queue";

    private final RoutingKeysProvider provider;

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(IoTCoreTopic.DATA_EXCHANGE);
    }

    @Bean
    public TopicExchange internalTopic() {
        return new TopicExchange(IoTCoreTopic.INTERNAL_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(SensorDataConsumer.INGRESS_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public Queue internalQueue() {
        return QueueBuilder.durable(RuleScenarioNotificationConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding internalBinding(Queue internalQueue, TopicExchange internalTopic) {
        var keys = provider.getInternalBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContainerType(ContainerTypeOptions.RULE_MANAGEMENT)
                .withContextType(ContextTypeOptions.RULE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(internalQueue).to(internalTopic).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topic) {
        var keys = provider.getSensorBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withLegitimacyType(DataLegitimacyOptions.CORRECT)
                .withOwnership(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .withRecords(RecordsOptions.WITH_RECORDS)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(queue).to(topic).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue ruleQueue() {
        return QueueBuilder.durable(RULE_MANAGEMENT_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding ruleBinding(Queue internalQueue, TopicExchange internalTopic) {
        var keys = provider.getInternalBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContainerType(ContainerTypeOptions.ALERT_DISPATCHER)
                .withContextType(ContextTypeOptions.RULE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.REQUEST)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(internalQueue).to(internalTopic).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }
}
