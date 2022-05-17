package sharespot.services.fleetmanagementbackend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.sensor.routing.keys.RecordsOptions;
import pt.sharespot.iot.core.sensor.routing.keys.data.*;
import sharespot.services.fleetmanagementbackend.application.RoutingKeysProvider;
import sharespot.services.fleetmanagementbackend.infrastructure.endpoint.amqp.listener.SensorDataConsumer;

import static sharespot.services.fleetmanagementbackend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_EXCHANGE;
import static sharespot.services.fleetmanagementbackend.infrastructure.boot.config.AmqpDeadLetterConfiguration.DEAD_LETTER_QUEUE;

@Configuration
public class AmqpConfiguration {

    private final RoutingKeysProvider provider;

    public AmqpConfiguration(RoutingKeysProvider provider) {
        this.provider = provider;
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(SensorDataConsumer.INGRESS_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(IoTCoreTopic.DATA_EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topic) {
        var keys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withRecords(RecordsOptions.WITH_RECORDS)
                .withLegitimacyType(DataLegitimacyOptions.CORRECT)
                .withGps(GPSDataOptions.WITH_GPS_DATA)
                .withTemperature(TemperatureDataOptions.WITHOUT_TEMPERATURE_DATA)
                .withTrigger(TriggerDataOptions.WITHOUT_TRIGGER_DATA)
                .withSoilMoisture(SoilMoistureDataOptions.WITHOUT_SOIL_MOISTURE_DATA)
                .withAirHumidity(AirHumidityDataOptions.WITHOUT_AIR_HUMIDITY_DATA)
                .withIlluminance(IlluminanceDataOptions.WITHOUT_ILLUMINANCE_DATA)
                .withCO(CODataOptions.WITHOUT_CO_DATA)
                .withCO2(CO2DataOptions.WITHOUT_CO2_DATA)
                .withPm10(PM10DataOptions.WITHOUT_PM10_DATA)
                .withPm2_5(PM2_5DataOptions.WITHOUT_PM2_5_DATA)
                .withNH3(NH3DataOptions.WITHOUT_NH3_DATA)
                .withAQI(AirQualityDataOptions.WITHOUT_AQI_DATA)
                .missingAsAny();
        if (keys.isPresent()) {
            return BindingBuilder.bind(queue).to(topic).with(keys.get().toString());
        }
        throw new RuntimeException("Error creating Routing Keys");
    }
}
