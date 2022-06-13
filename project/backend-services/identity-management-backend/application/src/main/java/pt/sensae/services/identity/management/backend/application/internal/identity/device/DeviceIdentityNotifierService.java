package pt.sensae.services.identity.management.backend.application.internal.identity.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceId;
import pt.sensae.services.identity.management.backend.domainservices.service.device.ViewDeviceDomainOwners;

@Service
public class DeviceIdentityNotifierService {

    private final ViewDeviceDomainOwners collector;

    private final DeviceIdentityEventHandlerService publisher;

    public DeviceIdentityNotifierService(ViewDeviceDomainOwners collector, DeviceIdentityEventHandlerService publisher) {
        this.collector = collector;
        this.publisher = publisher;
    }

    public void notify(DeviceId dto) {
        var collect = collector.collect(dto);
        publisher.publishUpdate(collect);
    }
}
