package pt.sensae.services.device.commander.backend.infrastructure.endpoint.amqp.internal.model;

public class DeviceCommandEntryDTOImpl {

    public String id;

    public String name;

    public String payload;

    public Integer port;

    public Integer subDeviceRef;

    public DeviceInformationDTOImpl device;
}
