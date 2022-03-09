package sharespot.services.fleetmanagementbackend.domain;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.fleetmanagementbackend.domain.model.domain.DomainId;
import sharespot.services.fleetmanagementbackend.domain.model.pastdata.GPSSensorDataFilter;

import java.util.stream.Stream;

public interface ProcessedSensorDataRepository {

    void insert(ProcessedSensorDataDTO dao);

    Stream<ProcessedSensorDataDTO> queryMultipleDevices(GPSSensorDataFilter filters, Stream<DomainId> domains);

    Stream<ProcessedSensorDataDTO> lastDataOfEachDevice(Stream<DomainId> domains);

    Stream<ProcessedSensorDataDTO> queryPastData(ProcessedSensorDataDTO dao, Integer timeSpanInMinutes);
}
