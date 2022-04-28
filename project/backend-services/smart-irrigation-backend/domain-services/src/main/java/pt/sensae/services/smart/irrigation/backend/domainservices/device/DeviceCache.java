package pt.sensae.services.smart.irrigation.backend.domainservices.device;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.exceptions.NotValidException;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.*;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.*;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.*;
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
            repository.add(new Device(deviceId, getDeviceType(dto), DeviceLedger.start(newEntry)));
            cache.put(deviceId, newEntry);
        } else if (!newEntry.sameAs(oldEntry.get())) {
            update(deviceId, newEntry);
        }
    }

    @NotNull
    private DeviceType getDeviceType(ProcessedSensorDataDTO dto) {
        DeviceType type;
        if (dto.hasProperty(PropertyName.TRIGGER)) {
            type = DeviceType.VALVE;
        } else if (dto.hasAllProperties(PropertyName.TEMPERATURE, PropertyName.AIR_HUMIDITY_RELATIVE_PERCENTAGE)) {
            type = DeviceType.STOVE_SENSOR;
        } else if (dto.hasAllProperties(PropertyName.TEMPERATURE, PropertyName.AIR_HUMIDITY_GRAMS_PER_CUBIC_METER)) {
            type = DeviceType.STOVE_SENSOR;
        } else if (dto.hasAllProperties(PropertyName.ILLUMINANCE, PropertyName.SOIL_MOISTURE)) {
            type = DeviceType.PARK_SENSOR;
        } else {
            throw new NotValidException("No Valid data packet found");
        }
        return type;
    }

    public Optional<LedgerEntry> get(DeviceId id) {
        return Optional.ofNullable(cache.getIfPresent(id)).or(() -> repository.fetchDeviceActiveLedgerEntry(id));
    }

    private void update(DeviceId id, LedgerEntry newEntry) {
        repository.openDeviceLedgerEntry(id, newEntry);
        cache.put(id, newEntry);
    }

    private LedgerEntry toLedgerEntry(ProcessedSensorDataDTO data) {
        var owners = Ownership.of(data.device.domains.read.stream().map(DomainId::of))
                .and(data.device.domains.readWrite.stream().map(DomainId::of));

        var name = DeviceName.of(data.device.name);

        var gpsPoint = GPSPoint.ofLatLong(data.getSensorData().gps.latitude, data.getSensorData().gps.longitude);

        if (data.hasProperty(PropertyName.ALTITUDE)) {
            gpsPoint = GPSPoint.ofLatLongAlt(data.getSensorData().gps.latitude, data.getSensorData().gps.longitude, data.getSensorData().gps.altitude);
        }

        var validCommandsNumber = data.getSensorCommands()
                .stream()
                .map(e -> ValveCommand.from(e.id))
                .filter(Optional::isPresent)
                .toList()
                .size();

        var control = validCommandsNumber == 2 && DeviceType.VALVE.equals(getDeviceType(data)) ?
                new RemoteControl(true) :
                new RemoteControl(false);

        var records = new DeviceRecords(data.device.records.entry.stream()
                .map(e -> RecordEntry.of(e.label, e.content))
                .collect(Collectors.toSet()));

        var content = new DeviceContent(name, records, gpsPoint, control);

        return new LedgerEntry(content, OpenDate.of(data.reportedAt), CloseDate.empty(), owners);
    }
}
