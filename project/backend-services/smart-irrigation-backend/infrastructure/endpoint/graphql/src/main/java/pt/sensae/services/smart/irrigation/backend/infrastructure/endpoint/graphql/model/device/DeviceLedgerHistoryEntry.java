package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device;

import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.GPSDataDetails;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.SensorDataHistoryDetails;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.RecordEntry;

import java.util.Set;

public record DeviceLedgerHistoryEntry(String name, GPSDataDetails gps, Set<RecordEntry> records,
                                       Set<? extends SensorDataHistoryDetails> data) {
}
