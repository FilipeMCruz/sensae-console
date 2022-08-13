package pt.sensae.services.device.management.flow.application;

import pt.sensae.services.device.management.flow.application.model.DeviceTopicMessage;

public interface DeviceInformationNotificationPublisher {

    void next(DeviceTopicMessage deviceTopicMessage);
}
