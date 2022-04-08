package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model;

import java.util.Set;

public record DeviceLedgerHistoryEntry(String name, GPSDataDetails gps, Set<RecordEntry> records,
                                       Set<? extends SensorDataHistoryDetails> data) {
}
