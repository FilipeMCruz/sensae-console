package pt.sensae.services.data.processor.slave.backend.domain;

public record DataTransformationNotification(SensorTypeId id, NotificationType type, DataTransformation information) {
}
