package pt.sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @RabbitListener(queues = "GPSDataQueue")
    public void receiveUpdate(String in) {
        try {
            handler.publish(new ObjectMapper().readValue(in, GPSData.class));
        } catch (Exception ignored) {
        }
    }
}
