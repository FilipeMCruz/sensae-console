package pt.sensae.services.identity.management.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.device.DeviceInformationInfoConsumer;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.device.DeviceInformationSyncConsumer;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sensae.services.identity.management.backend.application.RoutingKeysProvider;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.identity.device.DeviceIdentityRequestConsumer;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.identity.device.DeviceIdentityInitConsumer;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.identity.tenant.TenantIdentityInitConsumer;

import static pt.sensae.services.identity.management.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE;
import static pt.sensae.services.identity.management.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE;

@Configuration
public class AmqpConfiguration {

    private static final String DEVICE_MANAGEMENT_QUEUE = "internal.device.management.device.init.queue";
    private final RoutingKeysProvider provider;

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public TopicExchange masterExchange() {
        return new TopicExchange(IoTCoreTopic.INTERNAL_EXCHANGE);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue slaveQueue() {
        return QueueBuilder.durable(DeviceIdentityRequestConsumer.MASTER_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding bindingMaster(Queue slaveQueue, TopicExchange masterExchange) {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_IDENTITY)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withOperationType(OperationTypeOptions.REQUEST)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(slaveQueue).to(masterExchange).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue initTenantQueue() {
        return QueueBuilder.durable(TenantIdentityInitConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding initTenantBinding(Queue initTenantQueue, TopicExchange internalExchange) {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.TENANT_IDENTITY)
                .withOperationType(OperationTypeOptions.INIT)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(initTenantQueue).to(internalExchange).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue initDeviceIdentityQueue() {
        return QueueBuilder.durable(DeviceIdentityInitConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding initDeviceIdentityBinding(Queue initDeviceIdentityQueue, TopicExchange internalExchange) {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_IDENTITY)
                .withOperationType(OperationTypeOptions.INIT)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(initDeviceIdentityQueue).to(internalExchange).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue syncDeviceInformationQueue() {
        return QueueBuilder.durable(DeviceInformationSyncConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding syncDeviceInformationBinding(Queue syncDeviceInformationQueue, TopicExchange internalExchange) {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.SYNC)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(syncDeviceInformationQueue).to(internalExchange).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue infoDeviceInformationQueue() {
        return QueueBuilder.durable(DeviceInformationInfoConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding infoDeviceInformationBinding(Queue infoDeviceInformationQueue, TopicExchange internalExchange) {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(infoDeviceInformationQueue).to(internalExchange).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue initDeviceInformationQueue() {
        return QueueBuilder.durable(DEVICE_MANAGEMENT_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding initDeviceInformationBinding(Queue initDeviceInformationQueue, TopicExchange internalExchange) {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INIT)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(initDeviceInformationQueue).to(internalExchange).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
