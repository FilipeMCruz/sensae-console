package sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import sharespot.services.locationtrackingbackend.application.publishers.GPSDataPublisher;
import sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.dto.ProcessedSensorDataDTOImpl;
import sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.mapper.GPSDataMapper;

@Component
public class GPSSensorDataChangeListener {

    Logger logger = LoggerFactory.getLogger(GPSSensorDataChangeListener.class);

    private final GPSDataPublisher handler;

    public GPSSensorDataChangeListener(GPSDataPublisher handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = "GPS Data With Records Queue")
    public void receiveUpdate(ProcessedSensorDataDTOImpl in) {
        try {
            handler.publish(GPSDataMapper.dtoToDomain(in));
        } catch (Exception exception) {
            logger.warn("Data lost:" + in.dataId, exception);
        }
    }
}
