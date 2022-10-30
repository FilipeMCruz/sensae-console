package pt.sensae.services.data.decoder.master.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sensae.services.data.decoder.master.backend.application.RoutingKeysProvider;

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
    public Queue requestQueue() {
        return QueueBuilder.nonDurable(service.getDecoderRequestQueueName())
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding bindingUnknown() {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DATA_DECODER)
                .withContainerType(ContainerTypeOptions.DATA_DECODER)
                .withOperationType(OperationTypeOptions.UNKNOWN)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(requestQueue()).to(internalExchange()).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public Queue pingQueue() {
        return QueueBuilder.nonDurable(service.getDecoderPingQueueName())
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding bindingPing() {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DATA_DECODER)
                .withContainerType(ContainerTypeOptions.DATA_DECODER)
                .withOperationType(OperationTypeOptions.PING)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(pingQueue()).to(internalExchange()).with(keys.get().toString());
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
