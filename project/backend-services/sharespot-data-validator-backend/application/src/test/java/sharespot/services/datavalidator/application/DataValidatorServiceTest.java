package sharespot.services.datavalidator.application;

import org.junit.jupiter.api.Test;
import pt.sharespot.iot.core.routing.keys.RoutingKeys;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.data.SensorDataDetailsDTO;
import pt.sharespot.iot.core.sensor.data.types.GPSDataDTO;
import pt.sharespot.iot.core.sensor.device.DeviceInformationDTO;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataValidatorServiceTest {

    private final RoutingKeysProvider internal = new InternalRoutingKeysMock();

    private final RoutingKeys externalKeys;

    public DataValidatorServiceTest() {
        RoutingKeysProvider external = new ExternalRoutingKeysMock();

        var opt = external.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .from("proce.0.1.14.data.p.lgt92.default.n.u.u.y.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.#");
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
        var decide = service.decide(randomWithGPSData(gps), externalKeys);
        assertTrue(decide.isPresent());
        assertEquals("c", decide.get().legitimacy.value());
    }

    @Test
    void ensureValidDataIsClassifiedAsCorrect2() {
        DataValidatorService service = new DataValidatorService(internal);

        var gps = GPSDataDTO.ofLatLong(38.750244, -9.229148);
        var decide = service.decide(randomWithGPSData(gps), externalKeys);
        assertTrue(decide.isPresent());
        assertEquals("c", decide.get().legitimacy.value());
    }

    @Test
    void ensureWrongDataIsClassifiedAsIncorrect() {
        DataValidatorService service = new DataValidatorService(internal);

        var gps = GPSDataDTO.ofLatLong(-138.123422352, 2.23426624);
        var decide = service.decide(randomWithGPSData(gps), externalKeys);
        assertTrue(decide.isPresent());
        assertEquals("i", decide.get().legitimacy.value());
    }

    @Test
    void ensureWrongDataIsClassifiedAsIncorrectWithWrongAltitude() {
        DataValidatorService service = new DataValidatorService(internal);

        var gps = GPSDataDTO.ofLatLongAlt(38.750244, -9.229148, -3000.0F);
        var decide = service.decide(randomWithGPSData(gps), externalKeys);
        assertTrue(decide.isPresent());
        assertEquals("i", decide.get().legitimacy.value());
    }

    @Test
    void ensureWrongDataIsClassifiedAsIncorrectWithWrongAltitude2() {
        DataValidatorService service = new DataValidatorService(internal);

        var gps = GPSDataDTO.ofLatLongAlt(38.750244, -9.229148, 100000.0F);
        var decide = service.decide(randomWithGPSData(gps), externalKeys);
        assertTrue(decide.isPresent());
        assertEquals("i", decide.get().legitimacy.value());
    }

    private ProcessedSensorDataDTO randomWithGPSData(GPSDataDTO gps) {
        var device = new DeviceInformationDTO();
        device.id = UUID.randomUUID();
        device.name = "Test";
        var sensor = new SensorDataDetailsDTO().withGps(gps);
        return new ProcessedSensorDataDTO(UUID.randomUUID(), device, Instant.now().toEpochMilli(), Map.of(0, sensor));
    }
}
