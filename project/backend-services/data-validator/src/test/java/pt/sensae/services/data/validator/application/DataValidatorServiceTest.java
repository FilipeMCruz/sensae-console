package pt.sensae.services.data.validator.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.model.data.DataUnitReadingsDTO;
import pt.sharespot.iot.core.data.model.data.types.GPSDataDTO;
import pt.sharespot.iot.core.data.model.device.DeviceInformationDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class DataValidatorServiceTest {

    private final RoutingKeysProvider internal = new InternalRoutingKeysMock();

    private final DataRoutingKeys externalKeys;

    public DataValidatorServiceTest() {
        RoutingKeysProvider external = new ExternalRoutingKeysMock();

        var opt = external.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .from("proce.0.1.20.data.p.lgt92.default.n.u.u.y.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.n.#");
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

    private DataUnitDTO randomWithGPSData(GPSDataDTO gps) {
        var device = new DeviceInformationDTO();
        device.id = UUID.randomUUID();
        device.name = "Test";
        var sensor = new DataUnitReadingsDTO().withGps(gps);
        return new DataUnitDTO(UUID.randomUUID(), device, Instant.now().toEpochMilli(), Map.of(0, sensor));
    }
}
