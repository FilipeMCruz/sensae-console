package pt.sensae.services.device.commander.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.Set;
@RegisterForReflection
public class DeviceInformationDTOImpl {

    public String deviceId;

    public String name;

    public String downlink;

    public Set<DeviceRecordEntryDTOImpl> records;

    public Set<DeviceStaticSensorEntryDTOImpl> staticData;

    public Set<DeviceSubSensorDTOImpl> subSensors;

    public Set<DeviceCommandEntryDTOImpl> commands;
}
