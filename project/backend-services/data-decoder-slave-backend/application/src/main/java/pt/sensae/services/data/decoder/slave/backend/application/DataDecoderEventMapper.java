package pt.sensae.services.data.decoder.slave.backend.application;

import pt.sensae.services.data.decoder.slave.backend.domain.DataDecoderNotification;
import pt.sensae.services.data.decoder.slave.backend.domain.SensorTypeId;

public interface DataDecoderEventMapper {

    DataDecoderNotification dtoToDomain(DataDecoderNotificationDTO dto);

    SensorTypeIdDTO domainToDto(SensorTypeId dto);
}
