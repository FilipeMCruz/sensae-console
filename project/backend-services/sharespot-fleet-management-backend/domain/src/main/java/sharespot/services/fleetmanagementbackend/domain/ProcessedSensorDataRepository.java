package sharespot.services.fleetmanagementbackend.domain;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.fleetmanagementbackend.domain.model.domain.DomainId;
import sharespot.services.fleetmanagementbackend.domain.model.pastdata.GPSSensorDataFilter;

import java.util.List;
import java.util.Set;

public interface ProcessedSensorDataRepository {

    void insert(ProcessedSensorDataDTO dao);

    List<ProcessedSensorDataDTO> queryMultipleDevices(GPSSensorDataFilter filters, Set<DomainId> domains);

    List<ProcessedSensorDataDTO> lastDataOfEachDevice(Set<DomainId> domains);

    List<ProcessedSensorDataDTO> queryPastData(ProcessedSensorDataDTO dao, Integer timeSpanInMinutes);
}
