package sharespot.services.dataprocessor.domain;

public record DataTransformationNotification(SensorTypeId id, NotificationType type, DataTransformation information) {
}
