package sharespot.services.datadecoder.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.routing.exchanges.IoTCoreExchanges;
import pt.sharespot.iot.core.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import sharespot.services.datadecoder.application.RoutingKeysProvider;
import sharespot.services.datadecoder.infrastructure.endpoint.amqpegress.controller.SensorDataSupplier;
import sharespot.services.datadecoder.infrastructure.endpoint.amqpingress.controller.SensorDataConsumer;

@Configuration
public class AmqpConfiguration {

    public static final String MASTER_EXCHANGE = "Sharespot Data Decoder Master Exchange";

    public static final String MASTER_QUEUE = "Sharespot Data Decoder Master Exchange -> Sharespot Data Decoder Slave Queue";

    private final RoutingKeysProvider provider;

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public Queue slaveQueue() {
        return QueueBuilder.durable(MASTER_QUEUE)
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public FanoutExchange masterExchange() {
        return new FanoutExchange(MASTER_EXCHANGE);
    }

    @Bean
    Binding bindingMaster(Queue slaveQueue, FanoutExchange masterExchange) {
        return BindingBuilder.bind(slaveQueue).to(masterExchange);
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(IoTCoreExchanges.DATA_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(SensorDataConsumer.INGRESS_QUEUE)
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
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
