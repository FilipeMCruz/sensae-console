package pt.sharespot.services.locationtrackingbackend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPQueueConfig {

    @Bean
    public Queue queueGPS() {
        return new Queue("General GPS Data Queue", false);
    }

    @Bean
    public FanoutExchange exchangeGPS() {
        return new FanoutExchange("General GPS Data Exchange");
    }

    @Bean
    Binding bindingLGT92(Queue queueGPS, FanoutExchange exchangeGPS) {
        return BindingBuilder.bind(queueGPS).to(exchangeGPS);
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
