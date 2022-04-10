package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.smart.irrigation.backend.application.model.SensorDataHistoryDTO;

import java.util.Set;
import java.util.UUID;

public record SensorDataHistory(UUID id, Set<DeviceLedgerHistoryEntry> ledger) implements SensorDataHistoryDTO {
}
