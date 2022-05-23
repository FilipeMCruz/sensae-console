package pt.sensae.services.device.management.slave.backend.domain.model;


import pt.sharespot.iot.core.sensor.model.SensorDataDTO;

import java.util.List;

public record DeviceWithSubDevices(SensorDataDTO controller, List<SensorDataDTO> sensors) {
}
