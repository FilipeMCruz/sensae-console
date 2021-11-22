package sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.dto;

import java.util.Set;

public class DeviceRecordDTOImpl {
    public Set<DeviceRecordBasicEntryDTOImpl> entry;

    public DeviceRecordDTOImpl(Set<DeviceRecordBasicEntryDTOImpl> entry) {
        this.entry = entry;
    }

    public DeviceRecordDTOImpl() {

    }
}
