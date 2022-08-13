package pt.sensae.services.device.commander.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class DeviceCommandEntryDTOImpl {

    public String id;

    public String name;

    public String payload;

    public Integer port;

    public Integer subDeviceRef;

    public DeviceInformationDTOImpl device;
}
