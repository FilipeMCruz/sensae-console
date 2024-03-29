package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.model.device;

import java.util.Set;

public class DeviceInformationDTOImpl {

    public String deviceId;

    public String name;

    public String downlink;

    public Set<DeviceRecordEntryDTOImpl> records;

    public Set<DeviceStaticSensorEntryDTOImpl> staticData;

    public Set<DeviceSubSensorDTOImpl> subSensors;

    public Set<DeviceCommandEntryDTOImpl> commands;
}
