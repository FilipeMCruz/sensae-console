package sharespot.services.devicerecordsbackend.application;

import java.util.UUID;

public interface ProcessedSensorDataWithRecordDTO {
    
    UUID dataId();
    
    boolean hasGpsData();
    
    boolean hasTempCData();
}
