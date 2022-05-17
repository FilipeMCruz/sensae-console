package sharespot.services.datadecoder.infrastructure.endpoint.amqpinternal.model;

import sharespot.services.datadecoder.application.DataDecoderNotificationDTO;

public class DataDecoderNotificationDTOImpl implements DataDecoderNotificationDTO {

    public String sensorType;

    public DataDecoderNotificationTypeDTOImpl type;

    public DataDecoderDTOImpl information;
}
