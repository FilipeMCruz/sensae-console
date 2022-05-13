package pt.sensae.services.device.management.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domainservices.RecordCollector;

@Service
public class DeviceInformationNotifierService {

    private final RecordCollector collector;

    private final RecordEventHandlerService publisher;

    private final DeviceEventMapper eventMapper;

    public DeviceInformationNotifierService(RecordCollector collector, RecordEventHandlerService publisher, DeviceEventMapper eventMapper) {
        this.collector = collector;
        this.publisher = publisher;
        this.eventMapper = eventMapper;
    }

    public void notify(DeviceDTO dto) {
        var device = eventMapper.dtoToDomain(dto);
        var collect = collector.collect(device);
        publisher.publishUpdate(collect);
    }
}
