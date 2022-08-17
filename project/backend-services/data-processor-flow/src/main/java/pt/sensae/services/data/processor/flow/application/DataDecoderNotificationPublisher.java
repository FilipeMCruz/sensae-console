package pt.sensae.services.data.processor.flow.application;

import pt.sensae.services.data.processor.flow.application.model.SensorTypeTopicMessage;

public interface DataDecoderNotificationPublisher {

    void next(SensorTypeTopicMessage sensorTypeTopicMessage);
}
