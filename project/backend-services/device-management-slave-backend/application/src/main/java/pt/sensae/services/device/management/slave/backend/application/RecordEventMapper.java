package pt.sensae.services.device.management.slave.backend.application;


import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;

public interface RecordEventMapper {

    DeviceId dtoToDomain(DeviceIdDTO dto);
    
}
