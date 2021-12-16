package sharespot.services.fastdatastore.application;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

public interface ProcessedSensorDataMapper {

    ProcessedSensorDataDAO dtoToDao(ProcessedSensorDataDTO dto);
}
