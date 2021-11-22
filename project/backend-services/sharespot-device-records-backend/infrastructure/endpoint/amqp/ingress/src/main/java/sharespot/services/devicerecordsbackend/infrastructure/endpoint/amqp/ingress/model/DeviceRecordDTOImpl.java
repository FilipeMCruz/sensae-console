package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.model;

import java.util.Set;

public class DeviceRecordDTOImpl {
    public Set<DeviceRecordBasicEntryDTOImpl> entry;

    public DeviceRecordDTOImpl(Set<DeviceRecordBasicEntryDTOImpl> entry) {
        this.entry = entry;
    }

    public DeviceRecordDTOImpl() {

    }
}
