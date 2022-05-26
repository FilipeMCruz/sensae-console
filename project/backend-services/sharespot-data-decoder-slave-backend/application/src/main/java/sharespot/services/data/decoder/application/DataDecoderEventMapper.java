package sharespot.services.data.decoder.application;

import sharespot.services.data.decoder.domain.DataDecoderNotification;
import sharespot.services.data.decoder.domain.SensorTypeId;

public interface DataDecoderEventMapper {

    DataDecoderNotification dtoToDomain(DataDecoderNotificationDTO dto);

    SensorTypeIdDTO domainToDto(SensorTypeId dto);
}
