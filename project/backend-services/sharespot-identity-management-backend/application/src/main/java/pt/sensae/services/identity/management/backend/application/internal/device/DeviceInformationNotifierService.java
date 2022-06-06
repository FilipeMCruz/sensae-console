package pt.sensae.services.identity.management.backend.application.internal.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceId;
import pt.sensae.services.identity.management.backend.domainservices.service.device.ViewDeviceDomainOwners;

@Service
public class DeviceInformationNotifierService {

    private final ViewDeviceDomainOwners collector;

    private final DeviceInformationEventHandlerService publisher;

    public DeviceInformationNotifierService(ViewDeviceDomainOwners collector, DeviceInformationEventHandlerService publisher) {
        this.collector = collector;
        this.publisher = publisher;
    }

    public void notify(DeviceId dto) {
        var collect = collector.collect(dto);
        publisher.publishUpdate(collect);
    }
}
