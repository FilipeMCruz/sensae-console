package sharespot.services.identitymanagementbackend.application.internal.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domainservices.service.device.CollectAllDevices;

@Service
public class DeviceInitNotificationHandler {

    private final CollectAllDevices collector;

    private final DeviceIdentitySyncHandler handler;

    private final DeviceIdentityMapper mapper;

    public DeviceInitNotificationHandler(CollectAllDevices collector, DeviceIdentitySyncHandler handler, DeviceIdentityMapper mapper) {
        this.collector = collector;
        this.handler = handler;
        this.mapper = mapper;
    }

    public void publishCurrentState() {
        var collect = collector.collect();
        handler.publishState(collect.map(mapper::domainToDto));
    }
}
