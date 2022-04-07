package pt.sensae.services.smart.irrigation.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sensae.services.smart.irrigation.backend.application.RoutingKeysProvider;
import pt.sharespot.iot.core.routing.keys.*;
import pt.sharespot.iot.core.routing.keys.data.*;

import static pt.sensae.services.smart.irrigation.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE;
import static pt.sensae.services.smart.irrigation.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE;

@Configuration
public class AmqpConfiguration {

    public static final String PARK_INGRESS_QUEUE = "Sharespot Smart Irrigation Queue (park)";

    public static final String STOVE_INGRESS_QUEUE = "Sharespot Smart Irrigation Queue (stove)";

    public static final String TOPIC_EXCHANGE = "sensor.topic";

    private final RoutingKeysProvider provider;

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public Queue parkQueue() {
        return QueueBuilder.durable(PARK_INGRESS_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public Queue stoveQueue() {
        return QueueBuilder.durable(STOVE_INGRESS_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    Binding gardenBinding(Queue parkQueue, TopicExchange topic) {
        var keys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withRecords(RecordsOptions.WITH_RECORDS)
                .withLegitimacyType(DataLegitimacyOptions.CORRECT)
                .withGps(GPSDataOptions.WITH_GPS_DATA)
                .withSoilMoisture(SoilMoistureDataOptions.WITH_SOIL_MOISTURE_DATA)
                .withIlluminance(IlluminanceDataOptions.WITH_ILLUMINANCE_DATA)
                .withPermissions(PermissionsOptions.WITH_PERMISSIONS)
                .missingAsAny();

        if (keys.isPresent()) {
            return BindingBuilder.bind(parkQueue).to(topic).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    Binding stoveBinding(Queue stoveQueue, TopicExchange topic) {
        var keys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withRecords(RecordsOptions.WITH_RECORDS)
                .withLegitimacyType(DataLegitimacyOptions.CORRECT)
                .withGps(GPSDataOptions.WITH_GPS_DATA)
                .withHumidity(HumidityDataOptions.WITH_HUMIDITY_DATA)
                .withTemperature(TemperatureDataOptions.WITH_TEMPERATURE_DATA)
                .withPermissions(PermissionsOptions.WITH_PERMISSIONS)
                .missingAsAny();

        if (keys.isPresent()) {
            return BindingBuilder.bind(stoveQueue).to(topic).with(keys.get().toString());
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