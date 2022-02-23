package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sharespot.services.devicerecordsbackend.domain.model.permissions.DevicePermissions;
import sharespot.services.devicerecordsbackend.domain.model.permissions.PermissionsRepository;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.mapper.PermissionMapper;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.repository.PermissionsRepositoryPostgres;

@Repository
public class PermissionsRepositoryImpl implements PermissionsRepository {

    private final PermissionsRepositoryPostgres repo;

    public PermissionsRepositoryImpl(PermissionsRepositoryPostgres repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public void update(DevicePermissions permissions) {
        repo.deleteByDeviceId(permissions.device().value().toString());
        repo.save(PermissionMapper.postgresToDomain(permissions));
    }
}
