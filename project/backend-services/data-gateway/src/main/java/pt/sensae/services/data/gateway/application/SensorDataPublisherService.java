package pt.sensae.services.data.gateway.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pt.sharespot.iot.core.data.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.data.routing.keys.RecordsOptions;
import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SensorDataPublisherService {

    @Inject
    EventPublisher sensorDataPublisher;

    @Inject
    RoutingKeysProvider provider;

    @Inject
    ObjectMapper mapper;

    public void registerSensorData(String sensorDataDTO, String channel, String infoType, String sensorType) {

        InfoTypeOptions type;
        if ("decoded".equalsIgnoreCase(infoType)) {
            type = InfoTypeOptions.DECODED;
        } else if ("encoded".equalsIgnoreCase(infoType)) {
            type = InfoTypeOptions.ENCODED;
        } else {
            throw new NotValidException("Info Type must be of value encoded or decoded");
        }

        if (!channel.matches("[a-zA-Z\\d]{2,15}")) {
            throw new NotValidException("Channel info can only have a max of 15 letters or numbers");
        }

        if (!sensorType.matches("[a-zA-Z\\d]{2,15}")) {
            throw new NotValidException("Sensor Type info can only have a max of 15 letters or numbers");
        }

        try {
            ObjectNode jsonNode = (ObjectNode) mapper.readTree(sensorDataDTO);
            provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                    .withInfoType(type)
                    .withSensorTypeId(sensorType)
                    .withChannel(channel)
                    .withRecords(RecordsOptions.UNIDENTIFIED_RECORDS)
                    .withUnidentifiedData()
                    .build()
                    .ifPresent(routingKeys -> this.sensorDataPublisher.publish(MessageSupplied.create(jsonNode, routingKeys)));
        } catch (JsonProcessingException e) {
            throw new NotValidException("Invalid Data Unit");
        }
    }
}
