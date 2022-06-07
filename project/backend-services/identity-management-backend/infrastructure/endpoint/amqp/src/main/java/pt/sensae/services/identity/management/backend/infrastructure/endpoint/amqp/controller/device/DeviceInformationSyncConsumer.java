package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.device;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.internal.device.DeviceInformationNotificationHandler;
import pt.sensae.services.identity.management.backend.application.internal.device.DeviceNotificationDTO;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.device.DeviceNotificationDTOImpl;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

import java.io.IOException;
import java.util.Set;

@Service
public class DeviceInformationSyncConsumer {

    public static final String QUEUE = "internal.identity.management.device.sync.queue";

    private final DeviceInformationNotificationHandler notifier;

    private final ObjectMapper mapper;

    public DeviceInformationSyncConsumer(DeviceInformationNotificationHandler notifier, ObjectMapper mapper) {
        this.notifier = notifier;
        this.mapper = mapper;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveIndexEvent(Message in) throws IOException {
        var dto = mapper.readValue(in.getBody(), new TypeReference<Set<DeviceNotificationDTO>>() {
        }).stream();
        notifier.sync(dto);
    }
}
