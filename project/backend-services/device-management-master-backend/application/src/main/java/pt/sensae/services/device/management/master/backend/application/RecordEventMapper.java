package pt.sensae.services.device.management.master.backend.application;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

public interface RecordEventMapper {
    DeviceDTO domainToDto(DeviceId domain);

}
