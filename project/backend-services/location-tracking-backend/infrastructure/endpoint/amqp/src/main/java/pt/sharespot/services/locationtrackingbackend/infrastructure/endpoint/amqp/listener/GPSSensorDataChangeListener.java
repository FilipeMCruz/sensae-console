package pt.sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.sharespot.services.locationtrackingbackend.application.publishers.GPSDataPublisher;
import pt.sharespot.services.locationtrackingbackend.domain.sensor.gps.GPSData;

@Component
public class GPSSensorDataChangeListener {

    private final GPSDataPublisher handler;

    public GPSSensorDataChangeListener(GPSDataPublisher handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = "General GPS Data Queue")
    public void receiveUpdate(GPSData in) {
        try {
            handler.publish(in);
        } catch (Exception ignored) {
        }
    }
}
