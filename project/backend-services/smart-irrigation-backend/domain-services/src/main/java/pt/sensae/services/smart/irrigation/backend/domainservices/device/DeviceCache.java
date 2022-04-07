package pt.sensae.services.smart.irrigation.backend.domainservices.device;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.Device;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceType;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.*;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.DeviceContent;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.DeviceName;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.DeviceRecords;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.RecordEntry;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;

import java.time.Duration;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceCache {

    private final Cache<DeviceId, LedgerEntry> cache;

    private final DeviceRepository repository;

    public DeviceCache(DeviceRepository repository) {
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(50)
                .build();
        this.repository = repository;
    }

    public void updateIfNeeded(ProcessedSensorDataDTO dto) {
        var deviceId = DeviceId.of(dto.device.id);
        var newEntry = toLedgerEntry(dto);
        var oldEntry = get(deviceId);
        if (oldEntry.isEmpty()) {
            repository.add(new Device(deviceId, DeviceType.SENSOR, DeviceLedger.start(newEntry)));
            cache.put(deviceId, newEntry);
        } else if (!newEntry.sameAs(oldEntry.get())) {
            update(deviceId, newEntry);
        }
    }

    private Optional<LedgerEntry> get(DeviceId id) {
        return Optional.ofNullable(cache.getIfPresent(id))
                .or(() -> repository.fetchDeviceActiveLedgerEntry(id));
    }

    private void update(DeviceId id, LedgerEntry newEntry) {
        repository.closeDeviceLedgerEntry(id, newEntry.openAt().toClose());
        repository.openDeviceLedgerEntry(id, newEntry);
        cache.put(id, newEntry);
    }

    private LedgerEntry toLedgerEntry(ProcessedSensorDataDTO data) {
        var owners = Ownership.of(data.device.domains.read.stream().map(DomainId::of))
                .and(data.device.domains.readWrite.stream().map(DomainId::of));

        var name = DeviceName.of(data.device.name);

        var gpsPoint = GPSPoint.ofLatLong(data.data.gps.latitude.floatValue(),
                data.data.gps.longitude.floatValue());

        if (data.hasProperty(PropertyName.ALTITUDE)) {
            gpsPoint = GPSPoint.ofLatLongAlt(data.data.gps.latitude.floatValue(),
                    data.data.gps.longitude.floatValue(),
                    data.data.gps.altitude.floatValue());
        }

        var records = new DeviceRecords(data.device.records.entry.stream().map(e -> RecordEntry.of(e.label, e.content)).collect(Collectors.toSet()));

        var content = new DeviceContent(name, records, gpsPoint);

        return new LedgerEntry(content, OpenDate.of(data.reportedAt), CloseDate.empty(), owners);
    }
}
