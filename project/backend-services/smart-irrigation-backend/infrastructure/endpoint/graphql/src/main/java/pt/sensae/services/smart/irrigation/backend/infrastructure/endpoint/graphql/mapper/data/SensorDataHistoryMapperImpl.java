package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.mapper.data;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.mapper.data.SensorDataHistoryMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorDataHistoryDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceWithData;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.ParkPayload;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.StovePayload;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.*;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.DeviceLedgerHistoryEntryDTOImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.DeviceTypeDTOImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.RecordEntryDTOImpl;

import java.util.stream.Collectors;

@Service
public class SensorDataHistoryMapperImpl implements SensorDataHistoryMapper {

    @Override
    public SensorDataHistoryDTO toDto(DeviceWithData dto) {

        var type = switch (dto.type()) {
            case PARK_SENSOR -> DeviceTypeDTOImpl.PARK_SENSOR;
            case VALVE -> DeviceTypeDTOImpl.VALVE;
            case STOVE_SENSOR -> DeviceTypeDTOImpl.STOVE_SENSOR;
        };

        var ledger = dto.ledger().entries().stream().map(ledgerEntry -> {
            var entries = ledgerEntry.content().records()
                    .entries().stream()
                    .map(e -> new RecordEntryDTOImpl(e.label(), e.content()))
                    .collect(Collectors.toSet());

            var alt = ledgerEntry.content().coordinates().altitude() != null ?
                    ledgerEntry.content().coordinates().altitude().toString() :
                    "0";

            var gps = new GPSDataDetails(ledgerEntry.content().coordinates().latitude().toString(),
                    ledgerEntry.content().coordinates().longitude().toString(),
                    alt);

            var data = ledgerEntry.dataHistory().stream().map(dataEntry -> {
                if (dataEntry.payload() instanceof ParkPayload park) {
                    var lux = new IlluminanceDataDetails(park.illuminance().lux());
                    var moisture = new SoilMoistureDataDetails(park.soilMoisture().percentage());

                    return new ParkSensorDataHistoryDetails(dataEntry.id().value().toString(),
                            dataEntry.reportedAt().value().getEpochSecond(), lux, moisture);

                }
                var stove = (StovePayload) dataEntry.payload();
                var temperature = new TemperatureDataDetails(stove.temperature().celsius());
                var humidity = new HumidityDataDetails(stove.humidity().relativePercentage());

                return new StoveSensorDataHistoryDetails(dataEntry.id().value().toString(),
                        dataEntry.reportedAt().value().getEpochSecond(), temperature, humidity);
            }).collect(Collectors.toSet());

            return new DeviceLedgerHistoryEntryDTOImpl(ledgerEntry.content().name().value(), gps, entries, data);
        }).collect(Collectors.toSet());

        return new SensorDataHistory(dto.id().value(), type, ledger);
    }
}
