package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.device;

public class DeviceCommandEntryDTOImpl {

    public String id;

    public String name;

    public String payload;

    public Integer port;

    public Integer subDeviceRef;

    public DeviceInformationDTOImpl device;
}
