package pt.sensae.services.device.management.slave.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sensae.services.device.management.slave.backend.application.RoutingKeysProvider;
import pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.egress.controller.SensorDataSupplier;
import pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.ingress.controller.SensorDataConsumer;
import pt.sharespot.iot.core.routing.exchanges.IoTCoreExchanges;
import pt.sharespot.iot.core.routing.keys.*;

@Configuration
public class AmqpConfiguration {

    public static final String MASTER_EXCHANGE = "Sharespot Device Records Master Exchange";
    public static final String MASTER_QUEUE = "Sharespot Device Records Master Exchange -> Sharespot Device Records Slave Queue";

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
        var keys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withRecords(RecordsOptions.UNIDENTIFIED_RECORDS)
                .withOwnership(DomainOwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .withLegitimacyType(DataLegitimacyOptions.UNKNOWN)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(queue).to(topic).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }
}
