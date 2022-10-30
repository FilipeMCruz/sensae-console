package pt.sensae.services.data.processor.flow.domain;

public record DataTransformationNotification(SensorTypeId id, NotificationType type, DataTransformation information) {
}
