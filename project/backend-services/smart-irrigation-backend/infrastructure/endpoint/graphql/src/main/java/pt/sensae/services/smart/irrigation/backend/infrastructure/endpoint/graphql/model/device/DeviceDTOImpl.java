package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device;

import java.util.Set;
import java.util.UUID;

public record DeviceDTOImpl(String name, DeviceTypeDTOImpl type, UUID id, Set<RecordEntryDTOImpl> records, Boolean remoteControl) {
}
