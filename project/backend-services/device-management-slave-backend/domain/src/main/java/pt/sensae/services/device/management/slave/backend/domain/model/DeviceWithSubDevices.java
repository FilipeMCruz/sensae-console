package pt.sensae.services.device.management.slave.backend.domain.model;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

import java.util.List;

public record DeviceWithSubDevices(ProcessedSensorDataDTO controller, List<ProcessedSensorDataDTO> sensors) {
}