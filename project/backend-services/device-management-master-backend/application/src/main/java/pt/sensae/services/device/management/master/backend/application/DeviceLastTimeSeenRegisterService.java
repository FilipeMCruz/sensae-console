package pt.sensae.services.device.management.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domain.model.LastTimeSeenDeviceRepository;

@Service
public class DeviceLastTimeSeenRegisterService {

    private final DeviceEventMapper eventMapper;

    private final LastTimeSeenDeviceRepository repository;

    public DeviceLastTimeSeenRegisterService(DeviceEventMapper eventMapper, LastTimeSeenDeviceRepository repository) {
        this.eventMapper = eventMapper;
        this.repository = repository;
    }

    public void updateLastTimeSeen(DeviceDTO dto) {
        var device = eventMapper.dtoToDomain(dto);
        this.repository.update(device.id());
    }
}
