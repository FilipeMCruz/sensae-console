package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.model;

import sharespot.services.devicerecordsbackend.application.DeviceRecordEventDTO;

public class DeviceRecordsIndexEventDTOImpl implements DeviceRecordEventDTO {
    public DeviceRecordDTOImpl records;
    public String eventType;
}
