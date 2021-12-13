package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.model;

public class GPSDataDetailsDTOImpl {
    public Double latitude;
    public Double longitude;

    public boolean exists() {
        return latitude != null && longitude != null;
    }
}
