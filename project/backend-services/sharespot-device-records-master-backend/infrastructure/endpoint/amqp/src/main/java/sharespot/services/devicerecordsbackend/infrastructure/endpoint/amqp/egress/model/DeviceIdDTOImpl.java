package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.model;

import sharespot.services.devicerecordsbackend.application.DeviceDTO;

import java.util.UUID;

public class DeviceIdDTOImpl implements DeviceDTO {
    public UUID id;
}
