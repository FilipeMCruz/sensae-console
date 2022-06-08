package pt.sensae.services.data.decoder.master.backend.application;

import pt.sensae.services.data.decoder.master.backend.domain.DataDecoder;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;

public interface DataDecoderEventMapper {

    DataDecoderNotificationDTO domainToUpdatedDto(DataDecoder domain);

    DataDecoderNotificationDTO domainToDeletedDto(SensorTypeId domain);

    SensorTypeId dtoToDomain(SensorTypeIdDTO dto);
}
