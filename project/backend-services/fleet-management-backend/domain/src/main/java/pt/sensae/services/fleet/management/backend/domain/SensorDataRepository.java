package pt.sensae.services.fleet.management.backend.domain;

import pt.sensae.services.fleet.management.backend.domain.model.pastdata.GPSSensorDataFilter;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sensae.services.fleet.management.backend.domain.model.domain.DomainId;

import java.util.stream.Stream;

public interface SensorDataRepository {

    void insert(SensorDataDTO dao);

    Stream<SensorDataDTO> queryMultipleDevices(GPSSensorDataFilter filters, Stream<DomainId> domains);

    Stream<SensorDataDTO> lastDataOfEachDevice(Stream<DomainId> domains);

    Stream<SensorDataDTO> queryPastData(SensorDataDTO dao, Integer timeSpanInMinutes);
}
