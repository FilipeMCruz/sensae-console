package sharespot.services.lgt92gpsdataprocessor.application;

import java.util.UUID;

public interface OutSensorDataDTO {
    UUID dataId();
    
    boolean hasGpsData();
    
    boolean hasTempCData();
}
