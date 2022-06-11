package pt.sensae.services.data.gateway.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.sensor.routing.keys.RecordsOptions;

@Service
public class SensorDataPublisherService {

    private final EventPublisher sensorDataPublisher;

    private final RoutingKeysProvider provider;

    public SensorDataPublisherService(EventPublisher sensorDataPublisher, RoutingKeysProvider provider) {
        this.sensorDataPublisher = sensorDataPublisher;
        this.provider = provider;
    }

    public void registerSensorData(ObjectNode sensorDataDTO, String channel, String infoType, String sensorType) {

        InfoTypeOptions type;
        if ("decoded".equalsIgnoreCase(infoType)) {
            type = InfoTypeOptions.DECODED;
        } else if ("encoded".equalsIgnoreCase(infoType)) {
            type = InfoTypeOptions.ENCODED;
        } else {
            throw new NotValidException("Info Type must be of value encoded or decoded");
        }

        if (!channel.matches("\"[a-zA-Z\\\\d]+\"") || channel.length() > 15) {
            throw new NotValidException("Channel info can only have a max of 10 letters or numbers");
        }

        if (!sensorType.matches("\"[a-zA-Z\\\\d]+\"") || sensorType.length() > 15) {
            throw new NotValidException("Sensor Type info can only have a max of 10 letters or numbers");
        }

        provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withInfoType(type)
                .withSensorTypeId(sensorType)
                .withChannel(channel)
                .withRecords(RecordsOptions.UNIDENTIFIED_RECORDS)
                .withUnidentifiedData()
                .build()
                .ifPresent(routingKeys -> this.sensorDataPublisher.publish(MessageSupplied.create(sensorDataDTO, routingKeys)));
    }
}
