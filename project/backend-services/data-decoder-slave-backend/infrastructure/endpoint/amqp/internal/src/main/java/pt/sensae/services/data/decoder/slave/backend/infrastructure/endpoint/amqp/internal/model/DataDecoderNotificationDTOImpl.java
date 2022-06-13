package pt.sensae.services.data.decoder.slave.backend.infrastructure.endpoint.amqp.internal.model;

import pt.sensae.services.data.decoder.slave.backend.application.DataDecoderNotificationDTO;

public class DataDecoderNotificationDTOImpl implements DataDecoderNotificationDTO {

    public String sensorType;

    public DataDecoderNotificationTypeDTOImpl type;

    public DataDecoderDTOImpl information;
}
