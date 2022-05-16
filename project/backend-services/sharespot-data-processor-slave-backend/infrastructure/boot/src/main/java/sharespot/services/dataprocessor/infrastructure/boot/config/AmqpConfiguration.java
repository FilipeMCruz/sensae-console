package sharespot.services.dataprocessor.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.InfoTypeOptions;
import sharespot.services.dataprocessor.application.RoutingKeysProvider;
import sharespot.services.dataprocessor.infrastructure.endpoint.amqp.internal.controller.DataTransformationConsumer;
import sharespot.services.dataprocessor.infrastructure.endpoint.amqpingress.controller.SensorDataConsumer;

import static sharespot.services.dataprocessor.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE;
import static sharespot.services.dataprocessor.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE;

@Configuration
public class AmqpConfiguration {

    private final RoutingKeysProvider provider;

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public Queue slaveQueue() {
        return QueueBuilder.durable(DataTransformationConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public TopicExchange internalTopic() {
        return new TopicExchange(IoTCoreTopic.INTERNAL_EXCHANGE);
    }

    @Bean
    Binding bindingMaster(Queue slaveQueue, TopicExchange internalTopic) {
        var decoded = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DATA_PROCESSOR)
                .withContainerType(ContainerTypeOptions.DATA_PROCESSOR)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (decoded.isPresent()) {
            return BindingBuilder.bind(slaveQueue).to(internalTopic).with(decoded.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(IoTCoreTopic.DATA_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(SensorDataConsumer.INGRESS_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topic) {
        var decoded = provider.getSensorTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.DECODED)
                .missingAsAny();
        if (decoded.isPresent()) {
            return BindingBuilder.bind(queue).to(topic).with(decoded.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }
}
