package pt.sensae.services.data.decoder.flow.application;

import pt.sensae.services.data.decoder.flow.application.model.SensorTypeTopicMessage;

public interface DataDecoderNotificationPublisher {

    void next(SensorTypeTopicMessage sensorTypeTopicMessage);
}
