package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.information;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.DeviceInitNotificationHandler;

@Service
public class DeviceInformationInitConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(DeviceInformationInitConsumer.class);

    private final DeviceInitNotificationHandler service;

    public DeviceInformationInitConsumer(DeviceInitNotificationHandler service) {
        this.service = service;
    }

    @RabbitListener(queues = "#{queueNamingService.getDeviceManagementInitQueueName()}")
    public void receiveUpdate(Message in) {
        LOGGER.info("Received DEVICE_MANAGEMENT init request");
        this.service.publishCurrentState();
    }
}
