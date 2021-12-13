package sharespot.services.devicerecordsbackend.application;

public interface ProcessedSensorDataWithRecordDTO {
    
    boolean hasGpsData();
    
    boolean hasTempCData();
}
