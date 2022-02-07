package sharespot.services.datavalidator.application;

import org.junit.jupiter.api.Test;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.routing.keys.RoutingKeys;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.routing.keys.data.TemperatureDataOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.data.GPSDataDTO;
import pt.sharespot.iot.core.sensor.data.SensorDataDetailsDTO;
import pt.sharespot.iot.core.sensor.device.DeviceInformationDTO;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataValidatorServiceTest {

    private final RoutingKeysProvider internal = new InternalRoutingKeysMock();

    private final RoutingKeys externalKeys;

    public DataValidatorServiceTest() {
        RoutingKeysProvider external = new ExternalRoutingKeysMock();

        var opt = external.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .from("dataprocessorslave.dataprocessorslave.0.1.10.data.p.lgt92.default.u.u.y.n.n.n.n.n.n.#");
        if (opt.isPresent()) {
            externalKeys = opt.get();
        } else {
            throw new IllegalArgumentException();
        }
        System.out.println(externalKeys.details());
    }

    @Test
    void ensureValidDataIsClassifiedAsCorrect() {
        DataValidatorService service = new DataValidatorService(internal);

        var gps = GPSDataDTO.ofLatLong(45.756, 14.432);
        var decide = service.decide(randomWithGPSData(gps));
        assertTrue(decide.isPresent());
        assertEquals("c", decide.get().legitimacy);
    }

    @Test
    void ensureValidDataIsClassifiedAsCorrect2() {
        DataValidatorService service = new DataValidatorService(internal);

        var gps = GPSDataDTO.ofLatLong(38.750244, -9.229148);
        var decide = service.decide(randomWithGPSData(gps));
        assertTrue(decide.isPresent());
        assertEquals("c", decide.get().legitimacy);
    }

    @Test
    void ensureWrongDataIsClassifiedAsIncorrect() {
        DataValidatorService service = new DataValidatorService(internal);

        var gps = GPSDataDTO.ofLatLong(-138.123422352, 2.23426624);
        var decide = service.decide(randomWithGPSData(gps));
        assertTrue(decide.isPresent());
        assertEquals("i", decide.get().legitimacy);
    }

    private MessageConsumed<ProcessedSensorDataDTO> randomWithGPSData(GPSDataDTO gps) {
        var consumed = new MessageConsumed<ProcessedSensorDataDTO>();
        consumed.routingKeys = externalKeys;
        var device = new DeviceInformationDTO(UUID.randomUUID(), "Test");
        var sensor = new SensorDataDetailsDTO().withGps(gps);
        consumed.data = new ProcessedSensorDataDTO(UUID.randomUUID(), device, Instant.now().toEpochMilli(), sensor);
        return consumed;
    }
}
