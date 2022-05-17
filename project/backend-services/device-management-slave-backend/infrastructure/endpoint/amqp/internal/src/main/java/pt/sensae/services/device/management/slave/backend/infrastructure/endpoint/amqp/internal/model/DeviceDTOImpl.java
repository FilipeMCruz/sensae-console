package pt.sensae.services.device.management.slave.backend.infrastructure.endpoint.amqp.internal.model;

import pt.sensae.services.device.management.slave.backend.application.DeviceDTO;

public class DeviceDTOImpl implements DeviceDTO {

    public String deviceId;

    public String name;

    public String downlink;
}
