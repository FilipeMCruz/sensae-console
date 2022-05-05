package pt.sensae.services.smart.irrigation.backend.domain.model.data;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import pt.sensae.services.smart.irrigation.backend.domain.exceptions.DatabaseBusyException;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.query.DataQuery;

import java.sql.SQLException;
import java.util.stream.Stream;

public interface DataRepository {

    @Retryable(value = DatabaseBusyException.class,
            maxAttempts = 2, backoff = @Backoff(delay = 100))
    void store(Data data) throws DatabaseBusyException;

    Stream<Data> fetch(DataQuery query);

    Stream<Data> fetchLatest(Stream<DeviceId> deviceIds);
}
