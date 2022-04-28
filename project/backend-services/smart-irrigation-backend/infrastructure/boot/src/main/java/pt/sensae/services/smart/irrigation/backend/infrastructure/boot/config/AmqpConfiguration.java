package pt.sensae.services.smart.irrigation.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sensae.services.smart.irrigation.backend.application.RoutingKeysProvider;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.egress.controller.DeviceCommandSupplier;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.ingress.ParkSensorDataConsumer;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.ingress.StoveSensorDataConsumer;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.ingress.ValveSensorDataConsumer;
import pt.sharespot.iot.core.routing.exchanges.IoTCoreExchanges;
import pt.sharespot.iot.core.routing.keys.*;
import pt.sharespot.iot.core.routing.keys.data.*;

import static pt.sensae.services.smart.irrigation.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE;
import static pt.sensae.services.smart.irrigation.backend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE;

@Configuration
public class AmqpConfiguration {

    private final RoutingKeysProvider provider;

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public Queue parkQueue() {
        return QueueBuilder.durable(ParkSensorDataConsumer.INGRESS_QUEUE)
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
        return new TopicExchange(IoTCoreExchanges.DATA_EXCHANGE);
    }

    @Bean
    public TopicExchange commandExchange() {
        return new TopicExchange(DeviceCommandSupplier.COMMANDS_EXCHANGE);
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
                .withOwnership(DomainOwnershipOptions.WITH_DOMAIN_OWNERSHIP)
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
                .withOwnership(DomainOwnershipOptions.WITH_DOMAIN_OWNERSHIP)
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
                .withOwnership(DomainOwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .missingAsAny();

        if (keys.isPresent()) {
            return BindingBuilder.bind(valveQueue).to(topic).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }
}
