package pt.sensae.services.fleet.management.backend.domain;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import pt.sensae.services.fleet.management.backend.domain.exceptions.SenderException;
import pt.sensae.services.fleet.management.backend.domain.model.pastdata.GPSSensorDataFilter;
import pt.sensae.services.fleet.management.backend.domain.model.domain.DomainId;
import pt.sharespot.iot.core.data.model.DataUnitDTO;

import java.util.stream.Stream;

public interface SensorDataRepository {


    @Retryable(value = SenderException.class, backoff = @Backoff(delay = 100))
    void insert(DataUnitDTO dao) throws SenderException;

    Stream<DataUnitDTO> queryMultipleDevices(GPSSensorDataFilter filters, Stream<DomainId> domains);

    Stream<DataUnitDTO> lastDataOfEachDevice(Stream<DomainId> domains);

    Stream<DataUnitDTO> queryPastData(DataUnitDTO dao, Integer timeSpanInMinutes);
}
