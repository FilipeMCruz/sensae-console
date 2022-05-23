package sharespot.services.fleetmanagementbackend.domain;

import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import sharespot.services.fleetmanagementbackend.domain.model.domain.DomainId;
import sharespot.services.fleetmanagementbackend.domain.model.pastdata.GPSSensorDataFilter;

import java.util.stream.Stream;

public interface SensorDataRepository {

    void insert(SensorDataDTO dao);

    Stream<SensorDataDTO> queryMultipleDevices(GPSSensorDataFilter filters, Stream<DomainId> domains);

    Stream<SensorDataDTO> lastDataOfEachDevice(Stream<DomainId> domains);

    Stream<SensorDataDTO> queryPastData(SensorDataDTO dao, Integer timeSpanInMinutes);
}
