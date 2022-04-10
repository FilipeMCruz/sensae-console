package pt.sensae.services.smart.irrigation.backend.domainservices.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.Device;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceWithData;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataRepository;
import pt.sensae.services.smart.irrigation.backend.domainservices.device.model.LatestDataQuery;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LatestDataCollector {

    private final DataRepository repository;

    private final DeviceRepository deviceRepository;

    public LatestDataCollector(DataRepository repository, DeviceRepository deviceRepository) {
        this.repository = repository;
        this.deviceRepository = deviceRepository;
    }

    public Stream<DeviceWithData> fetch(LatestDataQuery query) {
        var deviceMap = deviceRepository.fetchLatest(query.value())
                .filter(withDevicesIn(query))
                .map(Device::toHistory)
                .collect(Collectors.toMap(DeviceWithData::id, Function.identity()));

        repository.fetchLatest(deviceMap.keySet().stream())
                .forEach(data -> deviceMap.get(data.deviceId())
                        .ledger()
                        .getEntryIn(data)
                        .ifPresent(entry -> entry.addData(data)));

        return deviceMap.values().stream();
    }

    private Predicate<Device> withDevicesIn(LatestDataQuery filter) {
        if (filter.deviceIds().isEmpty()) {
            return d -> true;
        }
        return d -> filter.deviceIds().contains(d.id());
    }
}
