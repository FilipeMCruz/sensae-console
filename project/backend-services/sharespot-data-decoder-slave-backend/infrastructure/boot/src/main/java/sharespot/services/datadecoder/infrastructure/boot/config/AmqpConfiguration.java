package sharespot.services.datadecoder.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.InfoTypeOptions;
import sharespot.services.datadecoder.application.RoutingKeysProvider;
import sharespot.services.datadecoder.infrastructure.endpoint.amqpinternal.controller.DataDecoderConsumer;
import sharespot.services.datadecoder.infrastructure.endpoint.amqpingress.controller.SensorDataConsumer;

import static sharespot.services.datadecoder.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE;
import static sharespot.services.datadecoder.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE;

@Configuration
public class AmqpConfiguration {

    public static final String MASTER_EXCHANGE = "Sharespot Data Decoder Master Exchange";

    private final RoutingKeysProvider provider;

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public Queue slaveQueue() {
        return QueueBuilder.durable(DataDecoderConsumer.QUEUE)
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
                .withContextType(ContextTypeOptions.DATA_DECODER)
                .withContainerType(ContainerTypeOptions.DATA_DECODER)
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
        var decoded = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.ENCODED)
                .missingAsAny();
        if (decoded.isPresent()) {
            return BindingBuilder.bind(queue).to(topic).with(decoded.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }
}
