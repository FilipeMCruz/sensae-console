package sharespot.services.datagateway.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.routing.keys.*;

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
                .withDefaultChannel()
                .withRecords(RecordsOptions.WITHOUT_RECORDS)
                .withGps(GPSDataOptions.WITHOUT_GPS_DATA)
                .withTempC(TempCDataOptions.WITHOUT_TEMPC_DATA)
                .withLegitimacyType(DataLegitimacyOptions.UNKNOWN)
                .build()
                .ifPresent(routingKeys -> this.sensorDataPublisher.publish(new MessageSupplied<>(routingKeys, sensorDataDTO)));
    }
}
