package pt.sensae.services.identity.management.backend.application.internal.identity.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domainservices.service.device.CollectAllDevices;

@Service
public class DeviceIdentityInitNotificationHandler {

    private final CollectAllDevices collector;

    private final DeviceIdentitySyncHandler handler;

    private final DeviceIdentityMapper mapper;

    public DeviceIdentityInitNotificationHandler(CollectAllDevices collector, DeviceIdentitySyncHandler handler, DeviceIdentityMapper mapper) {
        this.collector = collector;
        this.handler = handler;
        this.mapper = mapper;
    }

    public void publishCurrentState() {
        var collect = collector.collect();
        handler.publishState(collect.map(mapper::domainToDto));
    }
}
