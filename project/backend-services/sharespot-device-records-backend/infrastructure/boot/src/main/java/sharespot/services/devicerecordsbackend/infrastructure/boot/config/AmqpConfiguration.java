package sharespot.services.devicerecordsbackend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfiguration {

    public static final String INGRESS_EXCHANGE = "Sharespot LGT92 GPS Data Processor Exchange";
    public static final String INGRESS_QUEUE = "Sharespot LGT92 GPS Data Processor Exchange -> Sharespot Device Records Queue";
    public static final String EGRESS_EXCHANGE = "Sharespot Device Records Exchange";

    @Bean
    public Queue queue() {
        return new Queue(INGRESS_QUEUE, true);
    }

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(INGRESS_EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
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
