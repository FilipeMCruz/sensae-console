package pt.sensae.services.smart.irrigation.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sensae.services.smart.irrigation.backend.application.RoutingKeysProvider;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.ingress.alert.CloseValveAlertConsumer;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.ingress.alert.OpenValveAlertConsumer;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.ingress.data.ParkSensorDataConsumer;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.ingress.data.StoveSensorDataConsumer;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.ingress.data.ValveSensorDataConsumer;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.alert.routing.keys.AlertCategoryOptions;
import pt.sharespot.iot.core.alert.routing.keys.AlertSubCategoryOptions;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.sensor.routing.keys.RecordsOptions;
import pt.sharespot.iot.core.sensor.routing.keys.data.*;

import java.util.List;
import java.util.stream.Collectors;

import static pt.sensae.services.smart.irrigation.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE;
import static pt.sensae.services.smart.irrigation.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE;

@Configuration
public class AmqpConfiguration {

    @Value("#{'${sensae.alert.categories.open.valve}'.split(',')}")
    private List<String> openValveCategories;

    @Value("#{'${sensae.alert.categories.close.valve}'.split(',')}")
    private List<String> closeValveCategories;

    @Value("${sensae.channel}")
    public String channel;
    
    private final RoutingKeysProvider provider;

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public Queue parkQueue() {
        return QueueBuilder.durable(ParkSensorDataConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public Queue stoveQueue() {
        return QueueBuilder.durable(StoveSensorDataConsumer.INGRESS_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public Queue valveQueue() {
        return QueueBuilder.durable(ValveSensorDataConsumer.INGRESS_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(IoTCoreTopic.DATA_EXCHANGE);
    }

    @Bean
    public TopicExchange commandExchange() {
        return new TopicExchange(IoTCoreTopic.COMMAND_EXCHANGE);
    }

    @Bean
    Binding parkBinding(Queue parkQueue, TopicExchange topic) {
        var keys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withRecords(RecordsOptions.WITH_RECORDS)
                .withLegitimacyType(DataLegitimacyOptions.CORRECT)
                .withGps(GPSDataOptions.WITH_GPS_DATA)
                .withSoilMoisture(SoilMoistureDataOptions.WITH_SOIL_MOISTURE_DATA)
                .withIlluminance(IlluminanceDataOptions.WITH_ILLUMINANCE_DATA)
                .withOwnership(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .withChannel(channel)
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
                .withAirHumidity(AirHumidityDataOptions.WITH_AIR_HUMIDITY_DATA)
                .withTemperature(TemperatureDataOptions.WITH_TEMPERATURE_DATA)
                .withOwnership(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .withChannel(channel)
                .missingAsAny();

        if (keys.isPresent()) {
            return BindingBuilder.bind(stoveQueue).to(topic).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    Binding valveBinding(Queue valveQueue, TopicExchange topic) {
        var keys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withRecords(RecordsOptions.WITH_RECORDS)
                .withLegitimacyType(DataLegitimacyOptions.CORRECT)
                .withGps(GPSDataOptions.WITH_GPS_DATA)
                .withTrigger(TriggerDataOptions.WITH_TRIGGER_DATA)
                .withOwnership(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .withChannel(channel)
                .missingAsAny();

        if (keys.isPresent()) {
            return BindingBuilder.bind(valveQueue).to(topic).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }

    @Bean
    public TopicExchange alertExchange() {
        return new TopicExchange(IoTCoreTopic.ALERT_EXCHANGE);
    }

    @Bean
    public Queue openValveQueue() {
        return QueueBuilder.durable(OpenValveAlertConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public Queue closeValveQueue() {
        return QueueBuilder.durable(CloseValveAlertConsumer.QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Declarables openValveBinding(Queue openValveQueue, TopicExchange alertExchange) {
        var bindings = openValveCategories.stream().map(category -> {
            var keys = provider.getAlertBuilder(RoutingKeysBuilderOptions.CONSUMER)
                    .withOwnershipType(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                    .withCategoryType(AlertCategoryOptions.of("smartIrrigation"))
                    .withSubCategoryType(AlertSubCategoryOptions.of(category))
                    .missingAsAny();

            if (keys.isPresent()) {
                return BindingBuilder.bind(openValveQueue).to(alertExchange).with(keys.get().toString());
            }
            throw new RuntimeException("Error creating Routing Keys");
        }).collect(Collectors.toSet());
        return new Declarables(bindings);
    }

    @Bean
    Declarables closeValveBinding(Queue closeValveQueue, TopicExchange alertExchange) {
        var bindings = closeValveCategories.stream().map(category -> {
            var keys = provider.getAlertBuilder(RoutingKeysBuilderOptions.CONSUMER)
                    .withOwnershipType(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                    .withCategoryType(AlertCategoryOptions.of("smartIrrigation"))
                    .withSubCategoryType(AlertSubCategoryOptions.of(category))
                    .missingAsAny();

            if (keys.isPresent()) {
                return BindingBuilder.bind(closeValveQueue).to(alertExchange).with(keys.get().toString());
            }
            throw new RuntimeException("Error creating Routing Keys");
        }).collect(Collectors.toSet());
        return new Declarables(bindings);
    }
}
