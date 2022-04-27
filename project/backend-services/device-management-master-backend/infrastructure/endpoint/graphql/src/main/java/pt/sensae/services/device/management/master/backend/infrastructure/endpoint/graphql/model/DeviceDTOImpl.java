package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.device.management.master.backend.application.DeviceDTO;

import java.util.Set;

public class DeviceDTOImpl implements DeviceDTO {
    public String id;
    public String name;
    public String downlink;

    public Set<DeviceCommandDTOImpl> commands;
}
