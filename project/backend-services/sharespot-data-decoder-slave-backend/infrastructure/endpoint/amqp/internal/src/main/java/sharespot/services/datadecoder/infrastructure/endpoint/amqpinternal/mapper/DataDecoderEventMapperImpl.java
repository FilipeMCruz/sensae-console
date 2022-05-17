package sharespot.services.datadecoder.infrastructure.endpoint.amqpinternal.mapper;

import org.springframework.stereotype.Component;
import sharespot.services.datadecoder.application.DataDecoderEventMapper;
import sharespot.services.datadecoder.application.DataDecoderNotificationDTO;
import sharespot.services.datadecoder.application.SensorTypeIdDTO;
import sharespot.services.datadecoder.domain.*;
import sharespot.services.datadecoder.infrastructure.endpoint.amqpinternal.model.DataDecoderNotificationDTOImpl;
import sharespot.services.datadecoder.infrastructure.endpoint.amqpinternal.model.DataDecoderNotificationTypeDTOImpl;
import sharespot.services.datadecoder.infrastructure.endpoint.amqpinternal.model.SensorTypeIdDTOImpl;

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
