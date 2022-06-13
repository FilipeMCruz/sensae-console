package pt.sensae.services.data.decoder.slave.backend.domain;

public record DataDecoderNotification(SensorTypeId id, NotificationType type, DataDecoder information) {
}
