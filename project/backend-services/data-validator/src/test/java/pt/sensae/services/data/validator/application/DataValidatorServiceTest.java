package pt.sensae.services.data.validator.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.model.data.SensorDataDetailsDTO;
import pt.sharespot.iot.core.sensor.model.data.types.GPSDataDTO;
import pt.sharespot.iot.core.sensor.model.device.DeviceInformationDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class DataValidatorServiceTest {

    private final RoutingKeysProvider internal = new InternalRoutingKeysMock();

    private final SensorRoutingKeys externalKeys;

    public DataValidatorServiceTest() {
        RoutingKeysProvider external = new ExternalRoutingKeysMock();

        var opt = external.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .from("proce.0.1.18.data.p.lgt92.default.n.u.u.y.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.#");
        if (opt.isPresent()) {
            externalKeys = opt.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Test
    void ensureValidDataIsClassifiedAsCorrect() {
        DataValidatorService service = new DataValidatorService(internal);

        var gps = GPSDataDTO.ofLatLong(45.756, 14.432);
        var decide = service.decide(randomWithGPSData(gps), externalKeys);
        Assertions.assertTrue(decide.isPresent());
        Assertions.assertEquals("c", decide.get().legitimacy.value());
    }

    @Test
    void ensureValidDataIsClassifiedAsCorrect2() {
        DataValidatorService service = new DataValidatorService(internal);

        var gps = GPSDataDTO.ofLatLong(38.750244, -9.229148);
        var decide = service.decide(randomWithGPSData(gps), externalKeys);
        Assertions.assertTrue(decide.isPresent());
        Assertions.assertEquals("c", decide.get().legitimacy.value());
    }

    @Test
    void ensureWrongDataIsClassifiedAsIncorrect() {
        DataValidatorService service = new DataValidatorService(internal);

        var gps = GPSDataDTO.ofLatLong(-138.123422352, 2.23426624);
        var decide = service.decide(randomWithGPSData(gps), externalKeys);
        Assertions.assertTrue(decide.isPresent());
        Assertions.assertEquals("i", decide.get().legitimacy.value());
    }

    @Test
    void ensureWrongDataIsClassifiedAsIncorrectWithWrongAltitude() {
        DataValidatorService service = new DataValidatorService(internal);

        var gps = GPSDataDTO.ofLatLongAlt(38.750244, -9.229148, -3000.0F);
        var decide = service.decide(randomWithGPSData(gps), externalKeys);
        Assertions.assertTrue(decide.isPresent());
        Assertions.assertEquals("i", decide.get().legitimacy.value());
    }

    @Test
    void ensureWrongDataIsClassifiedAsIncorrectWithWrongAltitude2() {
        DataValidatorService service = new DataValidatorService(internal);

        var gps = GPSDataDTO.ofLatLongAlt(38.750244, -9.229148, 100000.0F);
        var decide = service.decide(randomWithGPSData(gps), externalKeys);
        Assertions.assertTrue(decide.isPresent());
        Assertions.assertEquals("i", decide.get().legitimacy.value());
    }

    private SensorDataDTO randomWithGPSData(GPSDataDTO gps) {
        var device = new DeviceInformationDTO();
        device.id = UUID.randomUUID();
        device.name = "Test";
        var sensor = new SensorDataDetailsDTO().withGps(gps);
        return new SensorDataDTO(UUID.randomUUID(), device, Instant.now().toEpochMilli(), Map.of(0, sensor));
    }
}
