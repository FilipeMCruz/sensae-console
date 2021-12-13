package sharespot.services.lgt92gpsdataprocessor.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sharespot.services.lgt92gpsdataprocessor.application.model.InfoTypeOptions;
import sharespot.services.lgt92gpsdataprocessor.application.model.RoutingKeys;
import sharespot.services.lgt92gpsdataprocessor.application.model.RoutingKeysBuilderOptions;

@Configuration
public class AmqpConfiguration {

    public static final String TOPIC_EXCHANGE = "sensor.topic";

    public static final String INGRESS_QUEUE = "Sharespot LGT92 GPS Data Processor Queue";
    public static final String EGRESS_EXCHANGE = "Sharespot GPS Data Processor Exchange";

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(INGRESS_QUEUE, true);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topic) {
        var lgt92 = RoutingKeys.builder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.DECODED)
                .withSensorTypeId("lgt92")
                .missingAsAny();
        if (lgt92.isPresent()) {
            return BindingBuilder.bind(queue).to(topic).with(lgt92.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public FanoutExchange exchangeType() {
        return new FanoutExchange(EGRESS_EXCHANGE);
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
