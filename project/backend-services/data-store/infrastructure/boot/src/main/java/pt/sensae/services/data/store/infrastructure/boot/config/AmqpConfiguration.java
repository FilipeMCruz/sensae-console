package pt.sensae.services.data.store.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pt.sensae.services.data.store.infrastructure.endpoint.amqp.controller.UnroutedDataConsumer;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sensae.services.data.store.application.RoutingKeysProvider;
import pt.sensae.services.data.store.infrastructure.endpoint.amqp.controller.SensorDataConsumer;


@Configuration
public class AmqpConfiguration {

    private final RoutingKeysProvider provider;
    public static final String UNROUTABLE_EXCHANGE = "unroutable.topic";

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public TopicExchange altExchange() {
        return ExchangeBuilder.topicExchange(UNROUTABLE_EXCHANGE).build();
    }

    @Bean
    public TopicExchange topic() {
        return ExchangeBuilder.topicExchange(IoTCoreTopic.DATA_EXCHANGE)
                .alternate(UNROUTABLE_EXCHANGE)
                .build();
    }

    @Bean
    @Profile("none")
    public Queue queue() {
        return QueueBuilder.nonDurable(SensorDataConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public Queue unroutedQueue() {
        return QueueBuilder.nonDurable(UnroutedDataConsumer.UNROUTABLE_QUEUE)
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }


    @Bean
    Binding unroutableBind() {
        return BindingBuilder.bind(unroutedQueue()).to(altExchange()).with("#");
    }

    @Bean
    @Profile("none")
    Binding binding() {
        var keys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContainerType(ContainerTypeOptions.DATA_GATEWAY)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(queue()).to(topic()).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }
}
