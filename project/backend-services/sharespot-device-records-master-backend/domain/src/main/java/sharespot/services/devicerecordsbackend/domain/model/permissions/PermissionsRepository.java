package sharespot.services.devicerecordsbackend.domain.model.permissions;

import sharespot.services.devicerecordsbackend.domain.model.DeviceId;

import java.util.List;
import java.util.Optional;

public interface PermissionsRepository {

    Optional<DevicePermissions> find(DeviceId deviceId);

    List<DevicePermissions> findAllInDomain(DomainId deviceId);
}
