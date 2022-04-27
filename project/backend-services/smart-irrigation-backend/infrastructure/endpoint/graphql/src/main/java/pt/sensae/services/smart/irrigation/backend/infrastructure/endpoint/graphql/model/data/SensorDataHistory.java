package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorDataHistoryDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.DeviceLedgerHistoryEntry;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.DeviceType;

import java.util.Set;
import java.util.UUID;

public record SensorDataHistory(UUID id, DeviceType type,
                                Set<DeviceLedgerHistoryEntry> ledger) implements SensorDataHistoryDTO {
}
