package pt.sensae.services.device.commander.application;

import pt.sensae.services.device.commander.application.model.DeviceTopicMessage;

public interface DeviceInformationNotificationPublisher {

    void next(DeviceTopicMessage deviceTopicMessage);
}
