package pt.sensae.services.smart.irrigation.backend.domainservices.data.mapper;

import pt.sensae.services.smart.irrigation.backend.domain.exceptions.NotValidException;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.Data;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.ReportTime;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.*;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.model.properties.PropertyName;

public class DataMapper {

    public static Data dtoToModel(DataUnitDTO dto) {
        var id = DataId.of(dto.dataId);
        var deviceId = DeviceId.of(dto.device.id);
        var reportedAt = ReportTime.of(dto.reportedAt);

        if (dto.hasAllProperties(PropertyName.TEMPERATURE, PropertyName.AIR_HUMIDITY_RELATIVE_PERCENTAGE)) {
            var temperature = Temperature.of(dto.getSensorData().temperature.celsius);
            var humidity = Humidity.of(dto.getSensorData().airHumidity.relativePercentage);
            var payload = new StovePayload(temperature, humidity);
            return new Data(id, deviceId, reportedAt, payload);
        } else if (dto.hasAllProperties(PropertyName.TEMPERATURE, PropertyName.AIR_HUMIDITY_GRAMS_PER_CUBIC_METER)) {
            //TODO: use this to calculate humidity https://www.aqua-calc.com/calculate/humidity
            throw new NotValidException("System can't process grams per cubic meter readings");
//            var temperature = Temperature.of(dto.getSensorData().temperature.celsius);
//            var humidity = Humidity.of(dto.getSensorData().airHumidity.gramsPerCubicMeter);
//            var payload = new StovePayload(temperature, humidity);
//            return new Data(id, deviceId, reportedAt, payload);
        } else if (dto.hasAllProperties(PropertyName.ILLUMINANCE, PropertyName.SOIL_MOISTURE)) {
            var lux = Illuminance.of(dto.getSensorData().illuminance.lux);
            var moisture = SoilMoisture.of(dto.getSensorData().soilMoisture.relativePercentage);
            var payload = new ParkPayload(lux, moisture);
            return new Data(id, deviceId, reportedAt, payload);
        } else if (dto.hasProperty(PropertyName.TRIGGER)) {
            var alertStatus = dto.getSensorData().trigger.value ? ValveStatusType.OPEN : ValveStatusType.CLOSE;
            var valve = new ValveStatus(alertStatus);
            var payload = new ValvePayload(valve);
            return new Data(id, deviceId, reportedAt, payload);
        } else {
            throw new NotValidException("No Valid data packet found");
        }
    }
}
