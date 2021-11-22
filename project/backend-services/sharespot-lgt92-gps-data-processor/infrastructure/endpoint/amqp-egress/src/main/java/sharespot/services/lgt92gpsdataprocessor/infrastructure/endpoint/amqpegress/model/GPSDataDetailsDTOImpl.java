package sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model;

public class GPSDataDetailsDTOImpl {
    
    public Double latitude;
    public Double longitude;

    public GPSDataDetailsDTOImpl(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
