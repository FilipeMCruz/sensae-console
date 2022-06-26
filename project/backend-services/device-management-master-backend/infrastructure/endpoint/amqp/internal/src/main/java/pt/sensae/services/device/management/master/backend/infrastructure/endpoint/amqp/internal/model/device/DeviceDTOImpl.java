package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.device;

import pt.sensae.services.device.management.master.backend.application.DeviceDTO;

public class DeviceDTOImpl implements DeviceDTO {

    public String deviceId;

    public String name;

    public String downlink;

    public DeviceDTOImpl() {
    }
}
