package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.devicerecordsbackend.domainservices.RecordAppender;

@Service
public class RecordAppenderService {

    private final RecordAppender appender;
    private final ProcessedSensorDataWithRecordMapper dataWithRecordMapper;
    private final ProcessedSensorDataMapper dataMapper;

    public RecordAppenderService(RecordAppender appender,
                                 ProcessedSensorDataWithRecordMapper dataWithRecordMapper,
                                 ProcessedSensorDataMapper dataMapper) {
        this.appender = appender;
        this.dataWithRecordMapper = dataWithRecordMapper;
        this.dataMapper = dataMapper;
    }

    public ProcessedSensorDataWithRecordsDTO tryToAppend(ProcessedSensorDataDTO dto) {
        var processedSensorData = dataMapper.dtoToDomain(dto);
        var processedSensorDataWithRecord = appender.appendRecord(processedSensorData);
        return dataWithRecordMapper.domainToDto(processedSensorDataWithRecord);
    }
}
