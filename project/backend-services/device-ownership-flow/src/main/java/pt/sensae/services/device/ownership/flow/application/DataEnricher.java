package pt.sensae.services.device.ownership.flow.application;

import pt.sensae.services.device.ownership.flow.domain.DeviceId;
import pt.sensae.services.device.ownership.flow.domain.DeviceOwnershipRepository;
import pt.sensae.services.device.ownership.flow.domain.DeviceWithAllPermissions;
import pt.sensae.services.device.ownership.flow.domain.DomainId;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class DataEnricher {

    @Inject
    DeviceOwnershipRepository cache;

    public Optional<SensorDataDTO> tryToAppend(SensorDataDTO dto) {
        return cache.findById(DeviceId.of(dto.device.id)).map(device -> {
            dto.device.domains = device.ownerDomains().stream().map(DomainId::value).collect(Collectors.toSet());
            return dto;
        });
    }

    public Optional<AlertDTO> tryToAppend(AlertDTO dto) {
        return cache.findAllById(dto.context.deviceIds.stream().map(DeviceId::of))
                .flatMap(devices -> {
                    if (devices.size() == 0) {
                        return Optional.empty();
                    }
                    var common = devices.get(0)
                            .ownerDomains()
                            .stream()
                            .map(DomainId::value)
                            .collect(Collectors.toSet());
                    for (DeviceWithAllPermissions device : devices) {
                        common.retainAll(device.ownerDomains()
                                .stream()
                                .map(DomainId::value)
                                .collect(Collectors.toSet()));
                    }
                    dto.context.domainIds = common;
                    return Optional.of(dto);
                });
    }
}
