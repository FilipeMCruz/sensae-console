package sharespot.services.fastdatastore.application;

public interface ProcessedSensorDataMapper {

    ProcessedSensorDataDAO dtoToDao(ProcessedSensorDataDTO dto);
}
