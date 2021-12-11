package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.model;

import sharespot.services.devicerecordsbackend.application.DeviceRecordIndexedEventDTO;

public class DeviceRecordsIndexEventDTOImpl implements DeviceRecordIndexedEventDTO {
    public DeviceRecordDTOImpl records;
    public String eventType;
}
