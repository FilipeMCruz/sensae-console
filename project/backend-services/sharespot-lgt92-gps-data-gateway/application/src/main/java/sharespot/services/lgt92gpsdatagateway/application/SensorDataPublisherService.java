package sharespot.services.lgt92gpsdatagateway.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.routing.keys.*;

@Service
public class SensorDataPublisherService {

    private final EventPublisher sensorDataPublisher;

    public SensorDataPublisherService(EventPublisher sensorDataPublisher) {
        this.sensorDataPublisher = sensorDataPublisher;
    }

    public void registerSensorData(ObjectNode sensorDataDTO) {
        RoutingKeys.supplierBuilder("lgt92gpsdatagateway", "datagateway")
                .withInfoType(InfoTypeOptions.DECODED)
                .withSensorTypeId("lgt92")
                .withDefaultChannel()
                .withRecords(RecordsOptions.WITHOUT_RECORDS)
                .withGps(GPSDataOptions.WITHOUT_GPS_DATA)
                .withTempC(TempCDataOptions.WITHOUT_TEMPC_DATA)
                .build()
                .ifPresent(routingKeys -> this.sensorDataPublisher.publish(new MessageSupplied<>(routingKeys, sensorDataDTO)));
    }
}
