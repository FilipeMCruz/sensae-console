package sharespot.services.datavalidator.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.routing.keys.GPSDataOptions;
import pt.sharespot.iot.core.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import sharespot.services.datavalidator.application.RoutingKeysProvider;

import static sharespot.services.datavalidator.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE;
import static sharespot.services.datavalidator.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE;

@Configuration
public class AmqpConfiguration {

    public static final String TOPIC_EXCHANGE = "sensor.topic";

    public static final String INGRESS_QUEUE = "Sharespot Data Validator Queue";

    private final RoutingKeysProvider provider;

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(INGRESS_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topic) {
        var decoded = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withLegitimacyType(DataLegitimacyOptions.UNKNOWN)
                .withGps(GPSDataOptions.WITH_GPS_DATA)
                .missingAsAny();
        if (decoded.isPresent()) {
            return BindingBuilder.bind(queue).to(topic).with(decoded.get().toString());
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
