package pt.sensae.services.device.commander.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum DeviceStaticDataLabelDTOImpl {
    GPS_LATITUDE,
    GPS_LONGITUDE,
    GPS_ALTITUDE,
    BATTERY_MIN_VOLTS,
    BATTERY_MAX_VOLTS,
    MIN_DISTANCE,
    MAX_DISTANCE
}
