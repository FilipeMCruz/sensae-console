package sharespot.services.datagateway.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.routing.keys.RecordsOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;

@Service
public class SensorDataPublisherService {

    private final EventPublisher sensorDataPublisher;

    private final RoutingKeysProvider provider;

    public SensorDataPublisherService(EventPublisher sensorDataPublisher, RoutingKeysProvider provider) {
        this.sensorDataPublisher = sensorDataPublisher;
        this.provider = provider;
    }

    public void registerSensorData(ObjectNode sensorDataDTO, String infoType, String sensorType) {

        InfoTypeOptions type;
        if (InfoTypeOptions.DECODED.value().equalsIgnoreCase(infoType)) {
            type = InfoTypeOptions.DECODED;
        } else if (InfoTypeOptions.ENCODED.value().equalsIgnoreCase(infoType)) {
            type = InfoTypeOptions.ENCODED;
        } else {
            throw new NotValidException("Info Type must be of value encoded or decoded");
        }

        provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withInfoType(type)
                .withSensorTypeId(sensorType)
                .withChannel("default")
                .withRecords(RecordsOptions.UNIDENTIFIED_RECORDS)
                .withUnidentifiedData()
                .build()
                .ifPresent(routingKeys -> this.sensorDataPublisher.publish(new MessageSupplied<>(routingKeys, sensorDataDTO)));
    }
}
