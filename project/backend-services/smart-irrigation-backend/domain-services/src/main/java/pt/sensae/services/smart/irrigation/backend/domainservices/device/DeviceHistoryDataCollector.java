package pt.sensae.services.smart.irrigation.backend.domainservices.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.Device;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceWithData;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.query.DataQuery;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeviceHistoryDataCollector {

    private final DataRepository dataRepository;

    private final DeviceRepository deviceRepository;

    public DeviceHistoryDataCollector(DataRepository dataRepository, DeviceRepository deviceRepository) {
        this.dataRepository = dataRepository;
        this.deviceRepository = deviceRepository;
    }

    public Stream<DeviceWithData> fetch(HistoryQuery query) {
        var collect = query.deviceIds().collect(Collectors.toSet());

        var deviceMap = deviceRepository.fetch(query.toDeviceQuery()).filter(d -> collect.contains(d.id()))
                .map(Device::toHistory)
                .collect(Collectors.toMap(DeviceWithData::id, Function.identity()));

        dataRepository.fetch(new DataQuery(collect.stream(), query.openDate(), query.closeDate()))
                .forEach(data -> deviceMap.get(data.deviceId())
                        .ledger()
                        .getEntryIn(data)
                        .ifPresent(entry -> entry.addData(data)));

        return deviceMap.values().stream();
    }
}
