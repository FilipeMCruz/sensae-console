package pt.sensae.services.data.decoder.flow.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pt.sensae.services.data.decoder.flow.application.model.DataDecoderNotificationDTO;

@RegisterForReflection
public class DataDecoderNotificationDTOImpl implements DataDecoderNotificationDTO {

    public String sensorType;

    public DataDecoderNotificationTypeDTOImpl type;

    public DataDecoderDTOImpl information;
}
