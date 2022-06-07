package pt.sensae.services.device.management.master.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sensae.services.device.management.master.backend.application.RoutingKeysProvider;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.identity.DeviceIdentityInfoConsumer;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.identity.DeviceIdentitySyncConsumer;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.information.DeviceInformationInitConsumer;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.information.DeviceInformationRequestConsumer;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import static pt.sensae.services.device.management.master.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE;
import static pt.sensae.services.device.management.master.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE;

@Configuration
public class AmqpConfiguration {

    public static final String IDENTITY_MANAGEMENT_QUEUE = "internal.identity.management.device.queue";

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
        return QueueBuilder.durable(DeviceInformationRequestConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding bindingMaster(Queue slaveQueue, TopicExchange masterExchange) {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.REQUEST)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(slaveQueue).to(masterExchange).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue initInformationQueue() {
        return QueueBuilder.durable(DeviceInformationInitConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding bindingInitInformation(Queue initInformationQueue, TopicExchange masterExchange) {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(initInformationQueue).to(masterExchange).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue initQueue() {
        return QueueBuilder.durable(IDENTITY_MANAGEMENT_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding initBinding(Queue initQueue, TopicExchange internalExchange) {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_IDENTITY)
                .withOperationType(OperationTypeOptions.INIT)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(initQueue).to(internalExchange).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue syncQueue() {
        return QueueBuilder.durable(DeviceIdentitySyncConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding syncBinding(Queue syncQueue, TopicExchange internalExchange) {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_IDENTITY)
                .withOperationType(OperationTypeOptions.SYNC)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(syncQueue).to(internalExchange).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue infoQueue() {
        return QueueBuilder.durable(DeviceIdentityInfoConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding infoBinding(Queue infoQueue, TopicExchange internalExchange) {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_IDENTITY)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(infoQueue).to(internalExchange).with(keys.get().toString());
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
