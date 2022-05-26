package pt.sensae.services.device.management.master.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sensae.services.device.management.master.backend.application.RoutingKeysProvider;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.DeviceNotificationConsumer;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

@Configuration
public class AmqpConfiguration {

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
        return QueueBuilder.durable(DeviceNotificationConsumer.MASTER_QUEUE)
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
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
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
