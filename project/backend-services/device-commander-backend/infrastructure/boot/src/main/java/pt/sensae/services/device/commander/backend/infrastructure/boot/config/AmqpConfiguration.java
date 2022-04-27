package pt.sensae.services.device.commander.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sensae.services.device.commander.backend.application.RoutingKeysProvider;
import pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.ingress.controller.controller.DeviceCommandConsumer;
import pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.controller.DeviceRecordConsumer;

@Configuration
public class AmqpConfiguration {

    public static final String MASTER_EXCHANGE = "Sharespot Device Records Master Exchange";
    public static final String COMMANDS_EXCHANGE = "commands.topic";

    private final RoutingKeysProvider provider;

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public Queue slaveQueue() {
        return QueueBuilder.durable(DeviceRecordConsumer.MASTER_QUEUE)
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding bindingMaster(Queue slaveQueue, FanoutExchange masterExchange) {
        return BindingBuilder.bind(slaveQueue).to(masterExchange);
    }

    @Bean
    public FanoutExchange masterExchange() {
        return new FanoutExchange(MASTER_EXCHANGE);
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
        return new TopicExchange(COMMANDS_EXCHANGE);
    }

    @Bean
    Binding bindingCommand(Queue commandQueue, TopicExchange commandExchange) {
        return BindingBuilder.bind(commandQueue).to(commandExchange).with("#");
    }
}
