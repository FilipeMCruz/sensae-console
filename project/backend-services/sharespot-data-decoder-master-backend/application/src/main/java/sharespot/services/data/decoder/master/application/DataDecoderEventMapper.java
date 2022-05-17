package sharespot.services.data.decoder.master.application;

import sharespot.services.data.decoder.master.domain.DataDecoder;
import sharespot.services.data.decoder.master.domain.SensorTypeId;

public interface DataDecoderEventMapper {

    DataDecoderNotificationDTO domainToUpdatedDto(DataDecoder domain);

    DataDecoderNotificationDTO domainToDeletedDto(SensorTypeId domain);

    SensorTypeId dtoToDomain(SensorTypeIdDTO dto);
}
