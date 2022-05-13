package pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.model;

import java.util.Set;

public class DeviceInformationDTOImpl {

    public String deviceId;

    public String name;

    public String downlink;

    public Set<DeviceRecordEntryDTOImpl> entries;

    public Set<DeviceSubSensorDTOImpl> subSensors;

    public Set<DeviceCommandEntryDTOImpl> commands;
}
