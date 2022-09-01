package pt.sensae.services.device.management.flow.domain;

import pt.sharespot.iot.core.data.model.DataUnitDTO;

import java.util.List;

public record DeviceWithSubDevices(DataUnitDTO controller, List<DataUnitDTO> sensors) {
}
