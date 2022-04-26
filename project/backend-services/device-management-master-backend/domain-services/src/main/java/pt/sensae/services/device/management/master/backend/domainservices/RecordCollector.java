package pt.sensae.services.device.management.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domain.model.records.DeviceRecords;
import pt.sensae.services.device.management.master.backend.domain.model.records.RecordsRepository;

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
