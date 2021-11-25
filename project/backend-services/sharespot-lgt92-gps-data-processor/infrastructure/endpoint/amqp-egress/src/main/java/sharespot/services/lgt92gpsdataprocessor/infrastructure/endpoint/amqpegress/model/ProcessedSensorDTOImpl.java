package sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model;

public class ProcessedSensorDTOImpl {

    public String name;

    public String id;

    public ProcessedSensorDTOImpl(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public ProcessedSensorDTOImpl() {
    }
}
