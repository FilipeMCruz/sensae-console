package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.mapper.data;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.mapper.SensorDataMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.SensorDataDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceType;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceWithData;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.ParkPayload;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.StovePayload;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.*;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.Device;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.RecordEntry;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;

import java.util.stream.Collectors;

@Service
public class SensorDataMapperImpl implements SensorDataMapper {

    @Override
    public SensorDataDTO toDto(ProcessedSensorDataDTO dto) {
        var entries = dto.device.records.entry.stream().map(e -> new RecordEntry(e.label, e.content)).collect(Collectors.toSet());
        var device = new Device(dto.device.name, dto.device.id, entries);

        var alt = dto.data.gps.altitude != null ? dto.data.gps.altitude.floatValue() : null;

        var gps = new GPSDataDetails(dto.data.gps.latitude.floatValue(), dto.data.gps.longitude.floatValue(), alt);
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

    @Override
    public SensorDataDTO toDto(DeviceWithData dto) {
        var any = dto.ledger().entries().stream().findAny();

        if (any.isEmpty()) {
            throw new RuntimeException("Error processing device data");
        }

        var singleData = any.get().dataHistory().stream().findAny();
        if (singleData.isEmpty()) {
            throw new RuntimeException("Error processing device data");
        }

        var entries = any.get().content()
                .records().entries().stream()
                .map(e -> new RecordEntry(e.label(), e.content())).collect(Collectors.toSet());

        var device = new Device(any.get().content().name().value(), dto.id().value(), entries);

        var gps = new GPSDataDetails(any.get().content().coordinates().altitude(),
                any.get().content().coordinates().longitude(),
                any.get().content().coordinates().altitude());

        //TODO: haven't decided what to do with this
        //It is only counting with sensor data, e.g. park or stove sensors, not possible valves info
        if (dto.type().equals(DeviceType.VALVE)) {
            throw new RuntimeException("Error processing device data");
        }

        SensorDataDetails payload;
        if (singleData.get().payload() instanceof ParkPayload park) {
            var lux = new IlluminanceDataDetails(park.illuminance().lux());
            var moisture = new SoilMoistureDataDetails(park.soilMoisture().percentage());
            payload = new ParkSensorDataDetails(gps, lux, moisture);
        } else {
            var stove = (StovePayload) singleData.get().payload();
            var temperature = new TemperatureDataDetails(stove.temperature().celsius());
            var humidity = new HumidityDataDetails(stove.humidity().gramsPerCubicMeter());
            payload = new StoveSensorDataDetails(gps, temperature, humidity);
        }
        return new SensorDataDTOImpl(singleData.get().id().value(),
                device, singleData.get().reportedAt().value().getEpochSecond(), payload);
    }
}
