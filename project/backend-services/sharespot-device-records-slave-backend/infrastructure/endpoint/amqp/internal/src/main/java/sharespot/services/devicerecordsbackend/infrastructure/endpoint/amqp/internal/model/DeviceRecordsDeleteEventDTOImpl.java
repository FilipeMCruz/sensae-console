package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.model;

import sharespot.services.devicerecordsbackend.application.DeviceRecordErasedEventDTO;

public class DeviceRecordsDeleteEventDTOImpl implements DeviceRecordErasedEventDTO {
    public DeviceIdDTOImpl records;
    public String eventType;
}
