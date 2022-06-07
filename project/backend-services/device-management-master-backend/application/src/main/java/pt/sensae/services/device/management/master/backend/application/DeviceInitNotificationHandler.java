package pt.sensae.services.device.management.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domainservices.DeviceInformationCollector;

@Service
public class DeviceInitNotificationHandler {

    private final DeviceInformationCollector collector;

    private final DeviceEventMapper mapper;

    private final DeviceInformationSyncHandler handler;

    public DeviceInitNotificationHandler(DeviceInformationCollector collector,
                                         DeviceEventMapper mapper,
                                         DeviceInformationSyncHandler handler) {
        this.collector = collector;
        this.mapper = mapper;
        this.handler = handler;
    }

    public void publishCurrentState() {
        var collect = collector.collect();
        handler.publishState(collect.map(mapper::domainToUpdatedDto));
    }
}
