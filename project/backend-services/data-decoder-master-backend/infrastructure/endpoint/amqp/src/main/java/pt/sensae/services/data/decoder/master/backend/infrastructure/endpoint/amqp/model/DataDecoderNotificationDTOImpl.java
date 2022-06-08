package pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.amqp.model;

import pt.sensae.services.data.decoder.master.backend.application.DataDecoderNotificationDTO;

public class DataDecoderNotificationDTOImpl implements DataDecoderNotificationDTO {

    public String sensorType;

    public DataDecoderNotificationTypeDTOImpl type;

    public DataDecoderDTOImpl information;
}
