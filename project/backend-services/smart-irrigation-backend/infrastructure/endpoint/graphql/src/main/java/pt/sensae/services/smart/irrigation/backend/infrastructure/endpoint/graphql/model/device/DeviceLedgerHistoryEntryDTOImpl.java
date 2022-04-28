package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device;

import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.GPSDataDetails;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.SensorDataHistoryDetails;

import java.util.Set;

public record DeviceLedgerHistoryEntryDTOImpl(String name, GPSDataDetails gps, Set<RecordEntryDTOImpl> records,
                                              Set<? extends SensorDataHistoryDetails> data) {
}
