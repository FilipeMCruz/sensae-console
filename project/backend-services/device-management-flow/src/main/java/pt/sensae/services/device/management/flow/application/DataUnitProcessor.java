package pt.sensae.services.device.management.flow.application;

import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.data.routing.keys.RecordsOptions;
import pt.sharespot.iot.core.keys.*;

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

    public void publish(MessageConsumed<DataUnitDTO, DataRoutingKeys> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(publisher::next);
    }

    private Optional<DataUnitDTO> inToOutData(DataUnitDTO node, DataRoutingKeys keys) {
        return mapper.tryToEnrich(node).map(device -> {
            device.sensors().forEach(sub -> this.publishSubSensor(sub, keys));
            return device.controller();
        });
    }

    private void publishSubSensor(DataUnitDTO data, DataRoutingKeys keys) {
        subSensorInToOutKeys(data, keys).map(sub -> MessageSupplied.create(data, sub)).ifPresent(publisher::next);
    }

    private Optional<DataRoutingKeys> inToOutKeys(DataUnitDTO data, DataRoutingKeys keys) {
        return provider.getDataTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withRecords(RecordsOptions.WITH_RECORDS)
                .from(keys);
    }

    private Optional<DataRoutingKeys> subSensorInToOutKeys(DataUnitDTO data, DataRoutingKeys keys) {
        return provider.getDataTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withRecords(RecordsOptions.UNIDENTIFIED_RECORDS)
                .withOwnership(OwnershipOptions.UNIDENTIFIED_DOMAIN_OWNERSHIP)
                .from(keys);
    }
}
