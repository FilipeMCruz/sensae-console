package pt.sensae.services.fleet.management.backend.domain;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import pt.sensae.services.fleet.management.backend.domain.exceptions.SenderException;
import pt.sensae.services.fleet.management.backend.domain.model.pastdata.GPSSensorDataFilter;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sensae.services.fleet.management.backend.domain.model.domain.DomainId;

import java.util.stream.Stream;

public interface SensorDataRepository {


    @Retryable(value = SenderException.class, backoff = @Backoff(delay = 100))
    void insert(SensorDataDTO dao) throws SenderException;

    Stream<SensorDataDTO> queryMultipleDevices(GPSSensorDataFilter filters, Stream<DomainId> domains);

    Stream<SensorDataDTO> lastDataOfEachDevice(Stream<DomainId> domains);

    Stream<SensorDataDTO> queryPastData(SensorDataDTO dao, Integer timeSpanInMinutes);
}
