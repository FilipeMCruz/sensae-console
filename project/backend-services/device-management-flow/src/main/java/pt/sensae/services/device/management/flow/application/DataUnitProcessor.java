package pt.sensae.services.device.management.flow.application;

import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.RecordsOptions;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class DataUnitProcessor {

    @Inject
    DeviceInformationEnricher mapper;

    @Inject
    RoutingKeysProvider provider;

    @Inject
    DataUnitPublisher publisher;

    public void publish(MessageConsumed<SensorDataDTO, SensorRoutingKeys> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(publisher::next);
    }

    private Optional<SensorDataDTO> inToOutData(SensorDataDTO node, SensorRoutingKeys keys) {
        return mapper.tryToEnrich(node).map(device -> {
            device.sensors().forEach(sub -> this.publishSubSensor(sub, keys));
            return device.controller();
        });
    }

    private void publishSubSensor(SensorDataDTO data, SensorRoutingKeys keys) {
        subSensorInToOutKeys(data, keys).map(sub -> MessageSupplied.create(data, sub)).ifPresent(publisher::next);
    }

    private Optional<SensorRoutingKeys> inToOutKeys(SensorDataDTO data, SensorRoutingKeys keys) {
        return provider.getDataTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withRecords(RecordsOptions.WITH_RECORDS)
                .from(keys);
    }

    private Optional<SensorRoutingKeys> subSensorInToOutKeys(SensorDataDTO data, SensorRoutingKeys keys) {
        return provider.getDataTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withRecords(RecordsOptions.UNIDENTIFIED_RECORDS)
                .withOwnership(OwnershipOptions.UNIDENTIFIED_DOMAIN_OWNERSHIP)
                .from(keys);
    }
}
