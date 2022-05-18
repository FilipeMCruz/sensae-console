package sharespot.services.data.decoder.infrastructure.endpoint.amqp.internal.model;

import sharespot.services.data.decoder.application.DataDecoderNotificationDTO;

public class DataDecoderNotificationDTOImpl implements DataDecoderNotificationDTO {

    public String sensorType;

    public DataDecoderNotificationTypeDTOImpl type;

    public DataDecoderDTOImpl information;
}
