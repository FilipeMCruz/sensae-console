package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.model;

public class DeviceRecordBasicEntryDTOImpl {
    public String label;
    public String content;

    public DeviceRecordBasicEntryDTOImpl(String label, String content) {
        this.label = label;
        this.content = content;
    }

    public DeviceRecordBasicEntryDTOImpl() {

    }
}