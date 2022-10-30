package pt.sensae.services.smart.irrigation.backend.domain.model.data;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import pt.sensae.services.smart.irrigation.backend.domain.exceptions.SenderException;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.query.DataQuery;

import java.util.stream.Stream;

public interface DataRepository {

    @Retryable(value = SenderException.class, backoff = @Backoff(delay = 100))
    void store(Data data) throws SenderException;

    Stream<Data> fetch(DataQuery query);

    Stream<Data> fetchLatest(Stream<DeviceId> deviceIds);
}
