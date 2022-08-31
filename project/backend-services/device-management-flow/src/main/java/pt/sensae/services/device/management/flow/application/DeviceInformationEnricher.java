package pt.sensae.services.device.management.flow.application;

import pt.sensae.services.device.management.flow.application.mapper.SensorDataWithDeviceInformationMapper;
import pt.sensae.services.device.management.flow.domain.DeviceInformationRepository;
import pt.sensae.services.device.management.flow.domain.DeviceWithSubDevices;
import pt.sensae.services.device.management.flow.domain.device.DeviceId;
import pt.sharespot.iot.core.data.model.DataUnitDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class DeviceInformationEnricher {

    @Inject
    DeviceInformationRepository cache;

    @Inject
    SensorDataWithDeviceInformationMapper dataWithRecordMapper;

    public Optional<DeviceWithSubDevices> tryToEnrich(DataUnitDTO dto) {
        return cache.findById(new DeviceId(dto.device.id))
                .map(deviceInformation -> dataWithRecordMapper.domainToDto(dto, deviceInformation));
    }
}
