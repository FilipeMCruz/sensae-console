package pt.sensae.services.notification.management.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sensae.services.notification.management.backend.application.RoutingKeysProvider;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.controller.AddressInitConsumer;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.controller.AlertConsumer;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.controller.TenantIdentityInfoConsumer;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.controller.TenantIdentitySyncConsumer;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import static pt.sensae.services.notification.management.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE;
import static pt.sensae.services.notification.management.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE;

@Configuration
public class AmqpConfiguration {

    private final RoutingKeysProvider provider;

    private final QueueNamingService service;

    public AmqpConfiguration(RoutingKeysProvider provider, QueueNamingService service) {
        this.provider = provider;
        this.service = service;
    }

    @Bean
    public TopicExchange internalExchange() {
        return new TopicExchange(IoTCoreTopic.INTERNAL_EXCHANGE);
    }

    @Bean
    public Queue internalInfoQueue() {
        return QueueBuilder.durable(service.getTenantIdentityInfoQueueName())
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding bindingMaster() {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.TENANT_IDENTITY)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(internalInfoQueue()).to(internalExchange()).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue internalSyncQueue() {
        return QueueBuilder.durable(service.getTenantIdentitySyncQueueName())
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding bindingSyncMaster() {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.TENANT_IDENTITY)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withOperationType(OperationTypeOptions.SYNC)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(internalSyncQueue()).to(internalExchange()).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public TopicExchange alertExchange() {
        return new TopicExchange(IoTCoreTopic.ALERT_EXCHANGE);
    }

    @Bean
    public Queue alertQueue() {
        return QueueBuilder.durable(service.getNotificationQueueName())
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding binding() {
        var keys = provider.getAlertTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withOwnershipType(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(alertQueue()).to(alertExchange()).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue initQueue() {
        return QueueBuilder.durable(service.getTenantIdentityInitQueueName())
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding initBinding() {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.TENANT_IDENTITY)
                .withOperationType(OperationTypeOptions.INIT)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(initQueue()).to(internalExchange()).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue initAddresseeQueue() {
        return QueueBuilder.durable(service.getAttendeesConfigurationInitQueueName())
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding initAddresseeBinding() {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.ADDRESSEE_CONFIGURATION)
                .withOperationType(OperationTypeOptions.INIT)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(initAddresseeQueue()).to(internalExchange()).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
