package sharespot.services.locationtrackingbackend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.routing.keys.*;
import sharespot.services.locationtrackingbackend.application.RoutingKeysProvider;

@Configuration
public class AmqpConfiguration {

    public static final String INGRESS_QUEUE = "Sharespot Location Tracking Queue";

    public static final String TOPIC_EXCHANGE = "sensor.topic";

    private final RoutingKeysProvider provider;

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public Queue queue() {
        return new Queue(INGRESS_QUEUE, true);
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topic) {
        var lgt92 = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withRecords(RecordsOptions.WITH_RECORDS)
                .withGps(GPSDataOptions.WITH_GPS_DATA)
                .missingAsAny();
        if (lgt92.isPresent()) {
            return BindingBuilder.bind(queue).to(topic).with(lgt92.get().toString());
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
