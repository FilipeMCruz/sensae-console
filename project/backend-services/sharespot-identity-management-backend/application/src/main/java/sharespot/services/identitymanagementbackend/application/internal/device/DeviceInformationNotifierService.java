package sharespot.services.identitymanagementbackend.application.internal.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceId;
import sharespot.services.identitymanagementbackend.domainservices.service.device.ViewDeviceDomainOwners;

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
