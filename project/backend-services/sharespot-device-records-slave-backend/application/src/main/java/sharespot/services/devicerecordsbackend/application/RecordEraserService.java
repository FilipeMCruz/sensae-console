package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domainservices.RecordEraser;

@Service
public class RecordEraserService {

    private final RecordEraser eraser;

    private final RecordEventMapper mapper;

    public RecordEraserService(RecordEraser eraser, RecordEventMapper mapper) {
        this.eraser = eraser;
        this.mapper = mapper;
    }

    public void erase(DeviceRecordErasedEventDTO dto) {
        var deviceId = mapper.dtoToDomain(dto);
        eraser.erase(deviceId);
    }
}
