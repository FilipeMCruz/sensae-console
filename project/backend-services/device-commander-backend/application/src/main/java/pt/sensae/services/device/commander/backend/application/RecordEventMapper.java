package pt.sensae.services.device.commander.backend.application;


import pt.sensae.services.device.commander.backend.domain.model.device.DeviceId;

public interface RecordEventMapper {

    DeviceId dtoToDomain(DeviceIdDTO dto);
    
}
