package pt.sensae.services.smart.irrigation.backend.domainservices.data.mapper;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.Data;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.ReportTime;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.*;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;

public class DataMapper {

    public static Data dtoToModel(ProcessedSensorDataDTO dto) {
        var id = DataId.of(dto.dataId);
        var deviceId = DeviceId.of(dto.device.id);
        var reportedAt = ReportTime.of(dto.reportedAt);

        if (dto.hasAllProperties(PropertyName.TEMPERATURE, PropertyName.HUMIDITY)) {
            var temperature = Temperature.of(dto.data.temperature.celsius.floatValue());
            var humidity = Humidity.of(dto.data.humidity.gramspercubicmeter.floatValue());
            var payload = new StovePayload(temperature, humidity);
            return new Data(id, deviceId, reportedAt, payload);
        } else {
            var lux = Illuminance.of(dto.data.illuminance.lux.floatValue());
            var moisture = SoilMoisture.of(dto.data.moisture.percentage.floatValue());
            var payload = new ParkPayload(lux, moisture);
            return new Data(id, deviceId, reportedAt, payload);
        }
    }
}
