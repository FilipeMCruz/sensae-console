package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import sharespot.services.devicerecordsbackend.domain.model.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.permissions.DevicePermissions;
import sharespot.services.devicerecordsbackend.domain.model.permissions.DomainId;
import sharespot.services.devicerecordsbackend.domain.model.permissions.PermissionsRepository;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.mapper.PermissionMapper;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.repository.PermissionsRepositoryPostgres;

import java.util.List;
import java.util.Optional;

@Repository
public class PermissionsRepositoryImpl implements PermissionsRepository {

    private final PermissionsRepositoryPostgres repo;

    public PermissionsRepositoryImpl(PermissionsRepositoryPostgres repo) {
        this.repo = repo;
    }

    @Override
    public Optional<DevicePermissions> find(DeviceId deviceId) {
        return repo.findByDeviceId(deviceId.value().toString())
                .map(PermissionMapper::postgresToDomain);
    }

    @Override
    public List<DevicePermissions> findAllInDomain(DomainId deviceId) {
        return repo.findByDomainId(deviceId.value().toString())
                .stream()
                .map(PermissionMapper::postgresToDomain)
                .toList();
    }
}
