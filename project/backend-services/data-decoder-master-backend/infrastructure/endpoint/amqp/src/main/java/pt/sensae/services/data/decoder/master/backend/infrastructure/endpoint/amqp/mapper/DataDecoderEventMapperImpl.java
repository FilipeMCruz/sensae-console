package pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.mapper;

import org.springframework.stereotype.Component;
import pt.sensae.services.data.decoder.master.backend.application.DataDecoderEventMapper;
import pt.sensae.services.data.decoder.master.backend.application.DataDecoderNotificationDTO;
import pt.sensae.services.data.decoder.master.backend.application.SensorTypeIdDTO;
import pt.sensae.services.data.decoder.master.backend.domain.DataDecoder;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model.DataDecoderDTOImpl;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model.DataDecoderNotificationTypeDTOImpl;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model.DataDecoderNotificationDTOImpl;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model.SensorTypeIdDTOImpl;

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
