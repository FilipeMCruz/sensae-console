package pt.sensae.services.device.management.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domainservices.DeviceInformationCollector;

@Service
public class DeviceInformationNotifierService {

    private final DeviceInformationCollector collector;

    private final DeviceInformationEventHandlerService publisher;

    private final DeviceEventMapper eventMapper;

    public DeviceInformationNotifierService(DeviceInformationCollector collector, DeviceInformationEventHandlerService publisher, DeviceEventMapper eventMapper) {
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
