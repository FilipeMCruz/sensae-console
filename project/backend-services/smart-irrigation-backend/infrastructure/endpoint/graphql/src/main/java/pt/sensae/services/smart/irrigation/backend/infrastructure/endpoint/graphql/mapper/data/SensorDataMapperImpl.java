package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.mapper.data;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.mapper.data.SensorDataMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorReadingDTO;
import pt.sensae.services.smart.irrigation.backend.domain.exceptions.NotValidException;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceWithData;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ValveCommand;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.ParkPayload;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.StovePayload;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.ValvePayload;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.ValveStatusType;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.*;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.DeviceDTOImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.DeviceTypeDTOImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.RecordEntryDTOImpl;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SensorDataMapperImpl implements SensorDataMapper {

    @Override
    public SensorReadingDTO toDto(SensorDataDTO dto) {
        var entries = dto.device.records.stream()
                .map(e -> new RecordEntryDTOImpl(e.label, e.content))
                .collect(Collectors.toSet());

        var alt = dto.getSensorData().gps.altitude != null ? dto.getSensorData().gps.altitude.toString() : "";

        var gps = new GPSDataDetails(dto.getSensorData().gps.latitude.toString(), dto.getSensorData().gps.longitude.toString(), alt);
        SensorDataDetails payload;
        DeviceTypeDTOImpl type;

        //TODO: use this to calculate humidity https://www.aqua-calc.com/calculate/humidity
        if (dto.hasAllProperties(PropertyName.TEMPERATURE, PropertyName.AIR_HUMIDITY_RELATIVE_PERCENTAGE)) {
            var temperature = new TemperatureDataDetails(dto.getSensorData().temperature.celsius);
            var humidity = new HumidityDataDetails(dto.getSensorData().airHumidity.relativePercentage);
            payload = new StoveSensorDataDetails(gps, temperature, humidity);
            type = DeviceTypeDTOImpl.STOVE_SENSOR;
        } else if (dto.hasAllProperties(PropertyName.TEMPERATURE, PropertyName.AIR_HUMIDITY_GRAMS_PER_CUBIC_METER)) {
            var temperature = new TemperatureDataDetails(dto.getSensorData().temperature.celsius);
            var humidity = new HumidityDataDetails(dto.getSensorData().airHumidity.gramsPerCubicMeter);
            payload = new StoveSensorDataDetails(gps, temperature, humidity);
            type = DeviceTypeDTOImpl.STOVE_SENSOR;
        } else if (dto.hasAllProperties(PropertyName.ILLUMINANCE, PropertyName.SOIL_MOISTURE)) {
            var lux = new IlluminanceDataDetails(dto.getSensorData().illuminance.lux);
            var moisture = new SoilMoistureDataDetails(dto.getSensorData().soilMoisture.relativePercentage);
            payload = new ParkSensorDataDetails(gps, lux, moisture);
            type = DeviceTypeDTOImpl.PARK_SENSOR;
        } else if (dto.hasProperty(PropertyName.TRIGGER)) {
            var alertStatus = dto.getSensorData().trigger.value ? ValveStatusDataDetailsType.OPEN : ValveStatusDataDetailsType.CLOSE;
            var valve = new ValveStatusDataDetails(alertStatus);
            payload = new ValveDataDetails(gps, valve);
            type = DeviceTypeDTOImpl.VALVE;
        } else {
            throw new NotValidException("No Valid data packet found");
        }
        var validCommandsNumber = dto.getSensorCommands()
                .stream()
                .map(e -> ValveCommand.from(e.id))
                .filter(Optional::isPresent)
                .toList()
                .size();

        var control = validCommandsNumber == 2 && type == DeviceTypeDTOImpl.VALVE;

        var device = new DeviceDTOImpl(dto.device.name, type, dto.device.id, entries, control);

        return new SensorReadingDTOImpl(dto.dataId, device, dto.reportedAt, payload);
    }

    @Override
    public SensorReadingDTO toDto(DeviceWithData dto) {
        var any = dto.ledger().entries().stream().findFirst();

        if (any.isEmpty()) {
            throw new RuntimeException("Error processing device data");
        }

        var singleData = any.get().dataHistory().stream().findAny();
        if (singleData.isEmpty()) {
            throw new RuntimeException("Error processing device data");
        }

        var entries = any.get().content()
                .records().entries().stream()
                .map(e -> new RecordEntryDTOImpl(e.label(), e.content())).collect(Collectors.toSet());

        var type = switch (dto.type()) {
            case PARK_SENSOR -> DeviceTypeDTOImpl.PARK_SENSOR;
            case VALVE -> DeviceTypeDTOImpl.VALVE;
            case STOVE_SENSOR -> DeviceTypeDTOImpl.STOVE_SENSOR;
        };

        var device = new DeviceDTOImpl(any.get().content().name().value(), type, dto.id().value(), entries, any.get()
                .content()
                .remoteControl()
                .value());

        var alt = any.get().content().coordinates().altitude() == null ? "0" : any.get()
                .content()
                .coordinates()
                .altitude()
                .toString();

        var gps = new GPSDataDetails(any.get().content().coordinates().latitude().toString(),
                any.get().content().coordinates().longitude().toString(),
                alt);

        SensorDataDetails payload;
        if (singleData.get().payload() instanceof ParkPayload park) {
            var lux = new IlluminanceDataDetails(park.illuminance().lux());
            var moisture = new SoilMoistureDataDetails(park.soilMoisture().percentage());
            payload = new ParkSensorDataDetails(gps, lux, moisture);
        } else if (singleData.get().payload() instanceof StovePayload stove) {
            var temperature = new TemperatureDataDetails(stove.temperature().celsius());
            var humidity = new HumidityDataDetails(stove.humidity().gramsPerCubicMeter());
            payload = new StoveSensorDataDetails(gps, temperature, humidity);
        } else if (singleData.get().payload() instanceof ValvePayload valve) {
            var alertStatus = valve.status().value().equals(ValveStatusType.OPEN) ?
                    ValveStatusDataDetailsType.OPEN : ValveStatusDataDetailsType.CLOSE;
            var valveStatus = new ValveStatusDataDetails(alertStatus);
            payload = new ValveDataDetails(gps, valveStatus);
        } else {
            throw new RuntimeException("Error processing device data");
        }
        return new SensorReadingDTOImpl(singleData.get().id().value(),
                device, singleData.get().reportedAt().value().toEpochMilli(), payload);
    }
}
