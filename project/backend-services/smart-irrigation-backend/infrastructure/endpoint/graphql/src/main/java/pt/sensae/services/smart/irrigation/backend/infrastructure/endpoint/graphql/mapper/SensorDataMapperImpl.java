package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.SensorDataDTO;
import pt.sensae.services.smart.irrigation.backend.application.SensorDataMapper;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.*;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;

import java.util.stream.Collectors;

@Service
public class SensorDataMapperImpl implements SensorDataMapper {

    @Override
    public SensorDataDTO toDto(ProcessedSensorDataDTO dto) {
        var entries = dto.device.records.entry.stream().map(e -> new RecordEntry(e.label, e.content)).collect(Collectors.toSet());
        var device = new Device(dto.device.name, dto.device.id, entries);

        var gps = new GPSDataDetails(dto.data.gps.latitude.floatValue(), dto.data.gps.longitude.floatValue(), dto.data.gps.altitude.floatValue());
        SensorDataDetails payload;
        if (dto.hasAllProperties(PropertyName.TEMPERATURE, PropertyName.HUMIDITY)) {
            var temperature = new TemperatureDataDetails(dto.data.temperature.celsius.floatValue());
            var humidity = new HumidityDataDetails(dto.data.humidity.gramspercubicmeter.floatValue());
            payload = new StoveSensorDataDetails(gps, temperature, humidity);
        } else {
            var lux = new IlluminanceDataDetails(dto.data.illuminance.lux.floatValue());
            var moisture = new SoilMoistureDataDetails(dto.data.moisture.percentage.floatValue());
            payload = new ParkSensorDataDetails(gps, lux, moisture);
        }
        return new SensorDataDTOImpl(dto.dataId, device, dto.reportedAt, payload);
    }
}
