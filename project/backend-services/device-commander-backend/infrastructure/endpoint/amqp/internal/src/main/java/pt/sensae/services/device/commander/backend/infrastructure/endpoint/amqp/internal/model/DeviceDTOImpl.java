package pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.model;

import pt.sensae.services.device.commander.backend.application.DeviceDTO;

public class DeviceDTOImpl implements DeviceDTO {

    public String deviceId;

    public String name;

    public String downlink;
}
