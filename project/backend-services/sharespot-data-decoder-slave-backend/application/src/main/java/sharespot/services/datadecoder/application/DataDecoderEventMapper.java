package sharespot.services.datadecoder.application;

import sharespot.services.datadecoder.domain.DataDecoderNotification;
import sharespot.services.datadecoder.domain.SensorTypeId;

public interface DataDecoderEventMapper {

    DataDecoderNotification dtoToDomain(DataDecoderNotificationDTO dto);

    SensorTypeIdDTO domainToDto(SensorTypeId dto);
}
