package sharespot.services.devicerecordsbackend.domain.model.records;

import java.util.Optional;
import java.util.Set;

public interface RecordsRepository {

    Optional<DeviceRecords> findByDeviceId(DeviceId id);

}
