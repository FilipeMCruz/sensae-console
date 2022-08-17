package pt.sensae.services.device.management.flow.domain;


import pt.sharespot.iot.core.sensor.model.SensorDataDTO;

import java.util.List;

public record DeviceWithSubDevices(SensorDataDTO controller, List<SensorDataDTO> sensors) {
}
