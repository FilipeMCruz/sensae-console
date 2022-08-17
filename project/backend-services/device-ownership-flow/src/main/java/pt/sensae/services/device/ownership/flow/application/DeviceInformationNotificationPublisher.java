package pt.sensae.services.device.ownership.flow.application;

import pt.sensae.services.device.ownership.flow.application.model.DeviceTopicMessage;

public interface DeviceInformationNotificationPublisher {

    void next(DeviceTopicMessage deviceTopicMessage);
}
