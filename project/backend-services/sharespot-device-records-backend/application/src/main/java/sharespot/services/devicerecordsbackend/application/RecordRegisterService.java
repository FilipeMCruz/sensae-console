package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domainservices.RecordHoarder;

@Service
public class RecordRegisterService {

    private final RecordHoarder hoarder;

    private final RecordMapper mapper;

    public RecordRegisterService(RecordHoarder hoarder, RecordMapper mapper) {
        this.hoarder = hoarder;
        this.mapper = mapper;
    }

    public DeviceRecordDTO register(DeviceRecordDTO dto) {
        var deviceRecords = mapper.dtoToDomain(dto);
        hoarder.hoard(deviceRecords);
        return dto;
    }
}
