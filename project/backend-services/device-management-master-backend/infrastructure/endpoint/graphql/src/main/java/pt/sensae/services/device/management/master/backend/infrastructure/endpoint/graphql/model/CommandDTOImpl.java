package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.device.management.master.backend.application.DeviceCommandDTO;

public class CommandDTOImpl implements DeviceCommandDTO {
    public String deviceId;
    public String commandId;
}
