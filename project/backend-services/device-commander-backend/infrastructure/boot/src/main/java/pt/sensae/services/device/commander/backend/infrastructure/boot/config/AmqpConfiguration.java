package pt.sensae.services.device.commander.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sensae.services.device.commander.backend.application.RoutingKeysProvider;
import pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.ingress.controller.controller.DeviceCommandConsumer;
import pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.controller.DeviceInformationConsumer;
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
    public Queue slaveQueue() {
        return QueueBuilder.durable(DeviceInformationConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding bindingMaster(Queue slaveQueue, TopicExchange masterExchange) {
        var keys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_MANAGEMENT)
                .withContainerType(ContainerTypeOptions.DEVICE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(slaveQueue).to(masterExchange).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public TopicExchange masterExchange() {
        return new TopicExchange(IoTCoreTopic.INTERNAL_EXCHANGE);
    }

    @Bean
    public Queue commandQueue() {
        return QueueBuilder.durable(DeviceCommandConsumer.COMMANDS_QUEUE)
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public TopicExchange commandExchange() {
        return new TopicExchange(IoTCoreTopic.COMMAND_EXCHANGE);
    }

    @Bean
    Binding bindingCommand(Queue commandQueue, TopicExchange commandExchange) {
        return BindingBuilder.bind(commandQueue).to(commandExchange).with("#");
    }
}
