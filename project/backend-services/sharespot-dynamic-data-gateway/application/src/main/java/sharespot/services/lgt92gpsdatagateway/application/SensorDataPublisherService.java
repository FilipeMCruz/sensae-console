package sharespot.services.lgt92gpsdatagateway.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.routing.keys.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class SensorDataPublisherService {

    @Value("${sharespot.sensor.type.path}")
    private String sensorTypePath;

    private final EventPublisher sensorDataPublisher;

    private final RoutingKeysProvider provider;

    public SensorDataPublisherService(EventPublisher sensorDataPublisher, RoutingKeysProvider provider) {
        this.sensorDataPublisher = sensorDataPublisher;
        this.provider = provider;
    }

    public void registerSensorData(ObjectNode sensorDataDTO) {
        provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withInfoType(InfoTypeOptions.DECODED)
                .withSensorTypeId(getValueAtPath(sensorDataDTO, sensorTypePath))
                .withDefaultChannel()
                .withRecords(RecordsOptions.WITHOUT_RECORDS)
                .withGps(GPSDataOptions.WITHOUT_GPS_DATA)
                .withTempC(TempCDataOptions.WITHOUT_TEMPC_DATA)
                .build()
                .ifPresent(routingKeys -> this.sensorDataPublisher.publish(new MessageSupplied<>(routingKeys, sensorDataDTO)));
    }

    static String getValueAtPath(JsonNode node, String path) {
        var pathList = new LinkedList<>(List.of(path.split("\\.")));
        var lastFromNew = pathList.pollLast();
        var valueNode = getInternalPath(node, pathList);
        return valueNode.get(lastFromNew).asText();
    }

    static JsonNode getInternalPath(JsonNode node, Queue<String> path) {
        if (path.size() == 0) return node;

        var poll = path.poll();
        JsonNode newNode = poll.matches("^d+$") ? node.path(Integer.parseInt(poll)) : node.path(poll);
        return getInternalPath(newNode, path);
    }
}
