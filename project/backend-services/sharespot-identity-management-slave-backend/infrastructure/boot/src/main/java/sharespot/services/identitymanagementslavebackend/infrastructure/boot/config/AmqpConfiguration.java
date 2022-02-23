package sharespot.services.identitymanagementslavebackend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.routing.keys.RecordsOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import sharespot.services.identitymanagementslavebackend.application.RoutingKeysProvider;

@Configuration
public class AmqpConfiguration {

    public static final String MASTER_EXCHANGE = "Sharespot Identity Management Master Exchange";
    public static final String MASTER_QUEUE = "Sharespot Identity Management Master Exchange -> Sharespot Identity Management Slave Queue";

    public static final String TOPIC_EXCHANGE = "sensor.topic";

    public static final String INGRESS_QUEUE = "Sharespot Identity Management Slave Queue";

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
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(INGRESS_QUEUE)
                .withArgument("x-dead-letter-exchange", AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topic) {
        var keys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withRecords(RecordsOptions.UNIDENTIFIED_RECORDS)
                .withLegitimacyType(DataLegitimacyOptions.CORRECT)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(queue).to(topic).with(keys.get().toString());
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
