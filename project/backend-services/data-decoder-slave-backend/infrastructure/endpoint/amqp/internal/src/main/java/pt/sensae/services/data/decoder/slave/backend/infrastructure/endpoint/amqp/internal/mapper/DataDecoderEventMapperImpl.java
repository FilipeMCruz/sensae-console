package pt.sensae.services.data.decoder.slave.backend.infrastructure.endpoint.amqp.internal.mapper;

import org.springframework.stereotype.Component;
import pt.sensae.services.data.decoder.slave.backend.application.DataDecoderEventMapper;
import pt.sensae.services.data.decoder.slave.backend.application.DataDecoderNotificationDTO;
import pt.sensae.services.data.decoder.slave.backend.application.SensorTypeIdDTO;
import pt.sensae.services.data.decoder.slave.backend.domain.*;
import pt.sensae.services.data.decoder.slave.backend.infrastructure.endpoint.amqp.internal.model.DataDecoderNotificationDTOImpl;
import pt.sensae.services.data.decoder.slave.backend.infrastructure.endpoint.amqp.internal.model.DataDecoderNotificationTypeDTOImpl;
import pt.sensae.services.data.decoder.slave.backend.infrastructure.endpoint.amqp.internal.model.SensorTypeIdDTOImpl;

@Component
public class DataDecoderEventMapperImpl implements DataDecoderEventMapper {

    @Override
    public DataDecoderNotification dtoToDomain(DataDecoderNotificationDTO dto) {

        var notificationDTO = (DataDecoderNotificationDTOImpl) dto;
        var id = dtoToDomain(notificationDTO.sensorType);
        var dataTransformationDTO = notificationDTO.information;
        if (notificationDTO.type.equals(DataDecoderNotificationTypeDTOImpl.UPDATE)) {
            var info = new DataDecoder(id, SensorTypeScript.of(dataTransformationDTO.script));
            return new DataDecoderNotification(id, NotificationType.UPDATE, info);
        } else {
            return new DataDecoderNotification(id, NotificationType.DELETE, null);
        }
    }

    public SensorTypeId dtoToDomain(String dto) {
        return SensorTypeId.of(dto);
    }

    @Override
    public SensorTypeIdDTO domainToDto(SensorTypeId domain) {
        var typeDto = new SensorTypeIdDTOImpl();
        typeDto.sensorType = domain.getValue();
        return typeDto;
    }
}
