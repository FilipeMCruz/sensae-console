package sharespot.services.identitymanagementbackend.application.internal;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.model.device.DeviceIdDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.device.ViewDeviceDomainOwners;

@Service
public class DeviceInformationNotifierService {

    private final ViewDeviceDomainOwners collector;

    private final DeviceInformationEventHandlerService publisher;

    private final NotificationEventMapper eventMapper;

    public DeviceInformationNotifierService(ViewDeviceDomainOwners collector,
                                            DeviceInformationEventHandlerService publisher,
                                            NotificationEventMapper eventMapper) {
        this.collector = collector;
        this.publisher = publisher;
        this.eventMapper = eventMapper;
    }

    public void notify(DeviceIdDTO dto) {
        var device = eventMapper.dtoToDomain(dto);
        var collect = collector.collect(device);
        publisher.publishUpdate(collect);
    }
}
