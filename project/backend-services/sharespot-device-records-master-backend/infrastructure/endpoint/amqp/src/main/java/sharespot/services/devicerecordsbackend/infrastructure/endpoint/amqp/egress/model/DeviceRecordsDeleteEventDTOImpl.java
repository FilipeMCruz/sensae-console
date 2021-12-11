package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.model;

import sharespot.services.devicerecordsbackend.application.DeviceRecordEventDTO;

public class DeviceRecordsDeleteEventDTOImpl implements DeviceRecordEventDTO {
    public DeviceIdDTOImpl records;
    public String eventType;
}
