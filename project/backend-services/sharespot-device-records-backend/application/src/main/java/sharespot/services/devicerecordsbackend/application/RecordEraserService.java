package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domainservices.RecordEraser;

@Service
public class RecordEraserService {

    private final RecordEraser eraser;

    private final RecordMapper mapper;


    public RecordEraserService(RecordEraser eraser, RecordMapper mapper) {
        this.eraser = eraser;
        this.mapper = mapper;
    }

    public DeviceDTO erase(DeviceDTO dto) {
        var deviceId = mapper.dtoToDomain(dto);
        var erased = eraser.erase(deviceId);
        return mapper.domainToDto(erased);
    }
}
