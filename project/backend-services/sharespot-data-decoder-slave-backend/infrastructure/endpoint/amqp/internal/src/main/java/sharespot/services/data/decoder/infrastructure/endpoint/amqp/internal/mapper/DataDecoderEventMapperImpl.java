package sharespot.services.data.decoder.infrastructure.endpoint.amqp.internal.mapper;

import org.springframework.stereotype.Component;
import sharespot.services.data.decoder.application.DataDecoderEventMapper;
import sharespot.services.data.decoder.application.DataDecoderNotificationDTO;
import sharespot.services.data.decoder.application.SensorTypeIdDTO;
import sharespot.services.data.decoder.domain.*;
import sharespot.services.data.decoder.infrastructure.endpoint.amqp.internal.model.DataDecoderNotificationDTOImpl;
import sharespot.services.data.decoder.infrastructure.endpoint.amqp.internal.model.DataDecoderNotificationTypeDTOImpl;
import sharespot.services.data.decoder.infrastructure.endpoint.amqp.internal.model.SensorTypeIdDTOImpl;

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
