package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domainservices.RecordEraser;

@Service
public class RecordEraserService {

    private final RecordEraser eraser;

    private final RecordMapper mapper;

    private final RecordEventHandlerService publisher;

    public RecordEraserService(RecordEraser eraser, RecordMapper mapper, RecordEventHandlerService publisher) {
        this.eraser = eraser;
        this.mapper = mapper;
        this.publisher = publisher;
    }

    public DeviceDTO erase(DeviceDTO dto) {
        var deviceId = mapper.dtoToDomain(dto);
        var erased = eraser.erase(deviceId);
        publisher.publishErased(erased);
        return mapper.domainToDto(erased);
    }
}
