package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

import pt.sensae.services.smart.irrigation.backend.application.model.SensorDataHistoryDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.DeviceLedgerHistoryEntry;

import java.util.Set;
import java.util.UUID;

public record SensorDataHistory(UUID id, Set<DeviceLedgerHistoryEntry> ledger) implements SensorDataHistoryDTO {
}
