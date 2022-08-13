package pt.sensae.services.data.decoder.flow.application.mapper;

import pt.sensae.services.data.decoder.flow.application.model.DataDecoderNotificationDTO;
import pt.sensae.services.data.decoder.flow.application.model.SensorTypeIdDTO;
import pt.sensae.services.data.decoder.flow.domain.DataDecoderNotification;
import pt.sensae.services.data.decoder.flow.domain.SensorTypeId;

public interface DataDecoderEventMapper {

    DataDecoderNotification dtoToDomain(DataDecoderNotificationDTO dto);

    SensorTypeIdDTO domainToDto(SensorTypeId dto);
}
