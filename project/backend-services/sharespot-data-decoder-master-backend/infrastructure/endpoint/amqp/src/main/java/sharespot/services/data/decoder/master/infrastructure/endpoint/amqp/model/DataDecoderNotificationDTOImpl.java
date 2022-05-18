package sharespot.services.data.decoder.master.infrastructure.endpoint.amqp.model;

import sharespot.services.data.decoder.master.application.DataDecoderNotificationDTO;

public class DataDecoderNotificationDTOImpl implements DataDecoderNotificationDTO {

    public String sensorType;

    public DataDecoderNotificationTypeDTOImpl type;

    public DataDecoderDTOImpl information;
}
