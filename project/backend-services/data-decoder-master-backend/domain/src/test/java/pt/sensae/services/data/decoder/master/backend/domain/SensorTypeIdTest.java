package pt.sensae.services.data.decoder.master.backend.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.sensae.services.data.decoder.master.backend.domain.exceptions.InvalidDecoderException;

class SensorTypeIdTest {

    @Test
    public void ensureLongSensorIdCantBeCreated() {
        Assertions.assertThrows(InvalidDecoderException.class,
                () -> SensorTypeId.of("aReallyReallyLongName"),
                "Invalid Id: can only have a max of 15 letters or numbers");
    }

    @Test
    public void ensureSensorIdWithSpecialCharsCantBeCreated() {
        Assertions.assertThrows(InvalidDecoderException.class,
                () -> SensorTypeId.of("n.n"),
                "Invalid Id: can only have a max of 15 letters or numbers");
    }

    @Test
    public void ensureSensorIdWithSpecialCharsCantBeCreated2() {
        Assertions.assertThrows(InvalidDecoderException.class,
                () -> SensorTypeId.of("n-n"),
                "Invalid Id: can only have a max of 15 letters or numbers");
    }

    @Test
    public void ensureSensorIdWithSpecialCharsCantBeCreated3() {
        Assertions.assertThrows(InvalidDecoderException.class,
                () -> SensorTypeId.of("n~n"),
                "Invalid Id: can only have a max of 15 letters or numbers");
    }

    @Test
    public void ensureSensorIdWithSpecialCharsCantBeCreated4() {
        Assertions.assertThrows(InvalidDecoderException.class,
                () -> SensorTypeId.of("n'n"),
                "Invalid Id: can only have a max of 15 letters or numbers");
    }

    @Test
    public void ensureSensorIdWithSpecialCharsCantBeCreated5() {
        Assertions.assertThrows(InvalidDecoderException.class,
                () -> SensorTypeId.of("n#n"),
                "Invalid Id: can only have a max of 15 letters or numbers");
    }

    @Test
    public void ensureSmallSensorIdCantBeCreated() {
        Assertions.assertThrows(InvalidDecoderException.class,
                () -> SensorTypeId.of("n"),
                "Invalid Id: can only have a max of 15 letters or numbers");
    }

    @Test
    public void ensureBasicSensorIdCanBeCreated() {
        var lgt92 = SensorTypeId.of("lgt92");
        Assertions.assertEquals("lgt92", lgt92.getValue());
    }

    @Test
    public void ensureSensorIdEqualsWorks() {
        var lgt92 = SensorTypeId.of("lgt92");
        var lgt922 = SensorTypeId.of("lgt92");
        var notlgt92 = SensorTypeId.of("notlgt92");
        Assertions.assertEquals(lgt92, lgt92);
        Assertions.assertEquals(lgt92, lgt922);
        Assertions.assertNotEquals(notlgt92, lgt922);
    }
}
