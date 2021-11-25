package sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.model;

import sharespot.services.devicerecordsbackend.application.DeviceRecordDTO;

import java.util.Set;

public class DeviceRecordDTOImpl implements DeviceRecordDTO {
    public DeviceDTOImpl device;
    public Set<RecordEntryDTOImpl> entries;
}
