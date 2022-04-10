package pt.sensae.services.smart.irrigation.backend.domainservices.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.Device;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceWithData;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.LedgerEntryWithData;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.query.DataQuery;
import pt.sensae.services.smart.irrigation.backend.domainservices.device.model.HistoryQuery;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.GardeningAreaCache;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeviceHistoryDataCollector {

    private final DataRepository dataRepository;

    private final DeviceRepository deviceRepository;

    private final GardeningAreaCache gardenCache;

    public DeviceHistoryDataCollector(DataRepository dataRepository, DeviceRepository deviceRepository, GardeningAreaCache gardenCache) {
        this.dataRepository = dataRepository;
        this.deviceRepository = deviceRepository;
        this.gardenCache = gardenCache;
    }

    public Stream<DeviceWithData> fetch(HistoryQuery query) {
        var gardens = gardenCache.fetchByIds(query.gardens().stream()).collect(Collectors.toSet());

        var deviceMap = deviceRepository.fetch(query.toDeviceQuery())
                .filter(getDevicePredicate(query.deviceIds()))
                .map(Device::toHistory)
                .collect(Collectors.toMap(DeviceWithData::id, Function.identity()));

        dataRepository.fetch(new DataQuery(deviceMap.keySet(), query.openDate(), query.closeDate()))
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

    private Predicate<Device> getDevicePredicate(Set<DeviceId> devices) {
        if (devices.isEmpty()) {
            return d -> true;
        }
        return d -> devices.contains(d.id());
    }

    private Predicate<LedgerEntryWithData> filterByGarden(Set<GardeningArea> gardens) {
        if (gardens.isEmpty()) {
            return d -> true;
        }
        return d -> gardens.stream().anyMatch(g -> g.area().contains(d.content().coordinates()));
    }
}
