package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.SensorDataHistoryDTO;
import pt.sensae.services.smart.irrigation.backend.application.SensorDataHistoryMapper;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceWithData;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.ParkPayload;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.StovePayload;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.*;

import java.util.stream.Collectors;

@Service
public class SensorDataHistoryMapperImpl implements SensorDataHistoryMapper {

    @Override
    public SensorDataHistoryDTO toDto(DeviceWithData dto) {

        var ledger = dto.ledger().entries().stream().map(ledgerEntry -> {
            var entries = ledgerEntry.content().records()
                    .entries().stream()
                    .map(e -> new RecordEntry(e.label(), e.content()))
                    .collect(Collectors.toSet());

            var gps = new GPSDataDetails(ledgerEntry.content().coordinates().latitude(),
                    ledgerEntry.content().coordinates().longitude(),
                    ledgerEntry.content().coordinates().altitude());

            var data = ledgerEntry.dataHistory().stream().map(dataEntry -> {
                if (dataEntry.payload() instanceof ParkPayload park) {
                    var lux = new IlluminanceDataDetails(park.illuminance().lux());
                    var moisture = new SoilMoistureDataDetails(park.soilMoisture().percentage());

                    return new ParkSensorDataHistoryDetails(dataEntry.id().value().toString(),
                            dataEntry.reportedAt().value().getEpochSecond(), lux, moisture);

                }
                var stove = (StovePayload) dataEntry.payload();
                var temperature = new TemperatureDataDetails(stove.temperature().celsius());
                var humidity = new HumidityDataDetails(stove.humidity().gramsPerCubicMeter());

                return new StoveSensorDataHistoryDetails(dataEntry.id().value().toString(),
                        dataEntry.reportedAt().value().getEpochSecond(), temperature, humidity);
            }).collect(Collectors.toSet());

            return new DeviceLedgerHistoryEntry(ledgerEntry.content().name().value(), gps, entries, data);
        }).collect(Collectors.toSet());

        return new SensorDataHistory(dto.id().value(), ledger);
    }
}
