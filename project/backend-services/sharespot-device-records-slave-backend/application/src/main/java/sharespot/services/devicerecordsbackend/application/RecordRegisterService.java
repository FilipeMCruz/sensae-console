package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domainservices.RecordHoarder;

@Service
public class RecordRegisterService {

    private final RecordHoarder hoarder;

    private final RecordEventMapper mapper;

    public RecordRegisterService(RecordHoarder hoarder, RecordEventMapper mapper) {
        this.hoarder = hoarder;
        this.mapper = mapper;
    }

    public void register(DeviceRecordIndexedEventDTO dto) {
        var deviceRecords = mapper.dtoToDomain(dto);
        hoarder.hoard(deviceRecords);
    }
}
