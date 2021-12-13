package sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import sharespot.services.locationtrackingbackend.application.publishers.GPSDataPublisher;
import sharespot.services.locationtrackingbackend.domain.message.MessageConsumed;
import sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.dto.ProcessedSensorDataDTOImpl;
import sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.mapper.GPSDataMapper;

@Component
public class GPSSensorDataChangeListener {
    
    public static final String INGRESS_QUEUE = "Sharespot Location Tracking Queue";

    private final GPSDataPublisher handler;

    public GPSSensorDataChangeListener(GPSDataPublisher handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(MessageConsumed<ProcessedSensorDataDTOImpl> in) {
        handler.publish(GPSDataMapper.dtoToDomain(in.data));
    }
}
