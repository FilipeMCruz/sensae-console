import {
  DeviceDTO,
} from '@frontend-services/identity-management/dto';
import {
  DeviceDomainPermission,
  DeviceInfo,
} from '@frontend-services/identity-management/model';

export class DeviceMapper {
  static dtoToModel(dto: DeviceDTO): DeviceInfo {
    const permissions = dto.domains.map(
      (d) =>
        new DeviceDomainPermission(d.oid)
    );
    return new DeviceInfo(dto.oid, dto.name, permissions);
  }
}
