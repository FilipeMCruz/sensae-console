package pt.sharespot.services.lgt92gpssensorgateway.infrastructure.endpoint.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfiguration {

    @Bean
    public FanoutExchange exchangeLGT92() {
        return new FanoutExchange("LGT92 GPS Data Exchange");
    }

    @Bean
    public Queue queueLGT92() {
        return new Queue("LGT92 GPS Data Queue", false);
    }

    @Bean
    Binding bindingLGT92(Queue queueLGT92, FanoutExchange exchangeLGT92) {
        return BindingBuilder.bind(queueLGT92).to(exchangeLGT92);
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
