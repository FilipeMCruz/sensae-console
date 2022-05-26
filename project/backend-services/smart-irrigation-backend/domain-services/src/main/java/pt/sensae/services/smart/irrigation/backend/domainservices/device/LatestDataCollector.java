package pt.sensae.services.smart.irrigation.backend.domainservices.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.Device;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceWithData;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.LedgerEntryWithData;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataRepository;
import pt.sensae.services.smart.irrigation.backend.domainservices.device.model.LatestDataQuery;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.GardeningAreaCache;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LatestDataCollector {

    private final DataRepository repository;

    private final DeviceRepository deviceRepository;

    private final GardeningAreaCache gardenCache;

    public LatestDataCollector(DataRepository repository, DeviceRepository deviceRepository, GardeningAreaCache gardenCache) {
        this.repository = repository;
        this.deviceRepository = deviceRepository;
        this.gardenCache = gardenCache;
    }

    public Stream<DeviceWithData> fetch(LatestDataQuery query) {
        var gardens = gardenCache.fetchByIds(query.gardens().stream()).collect(Collectors.toSet());

        var deviceMap = deviceRepository.fetchLatest(query.value())
                .filter(withDevicesIn(query))
                .map(Device::toHistory)
                .collect(Collectors.toMap(DeviceWithData::id, Function.identity()));

        repository.fetchLatest(deviceMap.keySet().stream())
                .forEach(data -> deviceMap.get(data.deviceId())
                        .ledger()
                        .getEntryIn(data)
                        .filter(filterByGarden(gardens))
                        .ifPresent(entry -> entry.addData(data)));

        return deviceMap.values().stream()
                .map(DeviceWithData::removeEmptyEntries)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    private Predicate<Device> withDevicesIn(LatestDataQuery filter) {
        if (filter.deviceIds().isEmpty()) {
            return d -> true;
        }
        return d -> filter.deviceIds().contains(d.id());
    }

    private Predicate<LedgerEntryWithData> filterByGarden(Set<GardeningArea> gardens) {
        if (gardens.isEmpty()) {
            return d -> true;
        }
        return d -> gardens.stream().anyMatch(g -> g.area().contains(d.content().coordinates()));
    }
}
