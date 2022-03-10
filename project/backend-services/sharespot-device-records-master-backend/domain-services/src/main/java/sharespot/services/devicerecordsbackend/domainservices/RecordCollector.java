package sharespot.services.devicerecordsbackend.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;
import sharespot.services.devicerecordsbackend.domain.model.records.RecordsRepository;

import java.util.stream.Stream;

@Service
public class RecordCollector {

    private final RecordsRepository repository;

    public RecordCollector(RecordsRepository repository) {
        this.repository = repository;
    }

    public Stream<DeviceRecords> collect() {
        return repository.findAll();
    }
}
