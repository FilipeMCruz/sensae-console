package pt.sensae.services.data.validator.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.routing.exchanges.IoTCoreExchanges;
import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.routing.keys.RecordsOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sensae.services.data.validator.backend.application.RoutingKeysProvider;
import pt.sensae.services.data.validator.backend.infrastructure.endpoint.amqpegress.controller.SensorDataSupplier;
import pt.sensae.services.data.validator.backend.infrastructure.endpoint.amqpingress.controller.SensorDataConsumer;

import static pt.sensae.services.data.validator.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE;
import static pt.sensae.services.data.validator.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE;

@Configuration
public class AmqpConfiguration {
    private final RoutingKeysProvider provider;

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(IoTCoreExchanges.DATA_EXCHANGE);
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
        var keys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withLegitimacyType(DataLegitimacyOptions.UNKNOWN)
                .withRecords(RecordsOptions.WITH_RECORDS)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(queue).to(topic).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }
}
