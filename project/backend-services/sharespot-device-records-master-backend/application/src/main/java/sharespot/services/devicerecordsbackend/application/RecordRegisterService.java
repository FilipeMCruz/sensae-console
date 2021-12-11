package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domainservices.RecordHoarder;

@Service
public class RecordRegisterService {

    private final RecordHoarder hoarder;

    private final RecordMapper mapper;

    private final RecordEventHandlerService publisher;

    public RecordRegisterService(RecordHoarder hoarder, RecordMapper mapper, RecordEventHandlerService publisher) {
        this.hoarder = hoarder;
        this.mapper = mapper;
        this.publisher = publisher;
    }

    public DeviceRecordDTO register(DeviceRecordDTO dto) {
        var deviceRecords = mapper.dtoToDomain(dto);
        hoarder.hoard(deviceRecords);
        publisher.publishIndex(deviceRecords);
        return dto;
    }
}
