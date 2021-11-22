package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domainservices.RecordCollector;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecordCollectorService {

    private final RecordCollector collector;

    private final RecordMapper mapper;

    public RecordCollectorService(RecordCollector collector, RecordMapper mapper) {
        this.collector = collector;
        this.mapper = mapper;
    }

    public Set<DeviceRecordDTO> records() {
        return collector.collect().stream().map(mapper::domainToDto).collect(Collectors.toSet());
    }
}
