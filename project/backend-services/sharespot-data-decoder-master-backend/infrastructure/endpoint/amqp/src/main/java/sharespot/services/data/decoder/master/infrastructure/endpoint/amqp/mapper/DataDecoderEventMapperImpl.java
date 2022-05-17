package sharespot.services.data.decoder.master.infrastructure.endpoint.amqp.mapper;

import org.springframework.stereotype.Component;
import sharespot.services.data.decoder.master.application.DataDecoderEventMapper;
import sharespot.services.data.decoder.master.application.DataDecoderNotificationDTO;
import sharespot.services.data.decoder.master.application.SensorTypeIdDTO;
import sharespot.services.data.decoder.master.domain.DataDecoder;
import sharespot.services.data.decoder.master.domain.SensorTypeId;
import sharespot.services.data.decoder.master.infrastructure.endpoint.amqp.model.DataDecoderDTOImpl;
import sharespot.services.data.decoder.master.infrastructure.endpoint.amqp.model.DataDecoderNotificationDTOImpl;
import sharespot.services.data.decoder.master.infrastructure.endpoint.amqp.model.DataDecoderNotificationTypeDTOImpl;
import sharespot.services.data.decoder.master.infrastructure.endpoint.amqp.model.SensorTypeIdDTOImpl;

@Component
public class DataDecoderEventMapperImpl implements DataDecoderEventMapper {

    @Override
    public DataDecoderNotificationDTO domainToUpdatedDto(DataDecoder domain) {
        var dto = new DataDecoderNotificationDTOImpl();
        dto.type = DataDecoderNotificationTypeDTOImpl.UPDATE;
        dto.sensorType = domain.id().getValue();
        var info = new DataDecoderDTOImpl();
        info.script = domain.script().value();
        dto.information = info;
        return dto;
    }

    @Override
    public DataDecoderNotificationDTO domainToDeletedDto(SensorTypeId domain) {
        var dto = new DataDecoderNotificationDTOImpl();
        dto.type = DataDecoderNotificationTypeDTOImpl.DELETE;
        dto.sensorType = domain.getValue();
        return dto;
    }

    @Override
    public SensorTypeId dtoToDomain(SensorTypeIdDTO dto) {
        var typeDto = (SensorTypeIdDTOImpl) dto;
        return SensorTypeId.of(typeDto.sensorType);
    }
}
