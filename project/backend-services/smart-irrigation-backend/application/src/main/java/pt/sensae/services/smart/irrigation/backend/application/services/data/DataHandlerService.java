package pt.sensae.services.smart.irrigation.backend.application.services.data;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domainservices.data.DataArchiver;
import pt.sensae.services.smart.irrigation.backend.domainservices.device.DeviceCache;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

@Service
public class DataHandlerService {

    private final DataArchiver archiver;
    private final DeviceCache deviceCache;
    private final SensorDataPublisher publisher;

    public DataHandlerService(DataArchiver archiver, DeviceCache deviceCache, SensorDataPublisher publisher) {
        this.archiver = archiver;
        this.deviceCache = deviceCache;
        this.publisher = publisher;
    }

    public void handle(ProcessedSensorDataDTO data) {
        this.publisher.publish(data);
        this.archiver.save(data);
        this.deviceCache.updateIfNeeded(data);
    }
}
