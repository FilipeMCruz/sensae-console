package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorDataHistoryDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.DeviceLedgerHistoryEntryDTOImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.DeviceTypeDTOImpl;

import java.util.Set;
import java.util.UUID;

public record SensorDataHistory(UUID id, DeviceTypeDTOImpl type,
                                Set<DeviceLedgerHistoryEntryDTOImpl> ledger) implements SensorDataHistoryDTO {
}
