import {DeviceDTO, DevicePermissionDTO} from "@frontend-services/identity-management/dto";
import {DeviceDomainPermission, DeviceInfo, DevicePermissionType} from "@frontend-services/identity-management/model";

export class DeviceMapper {

  private static dtoTypeToModel(dto: DevicePermissionDTO): DevicePermissionType {
    if (dto === DevicePermissionDTO.READ) {
      return DevicePermissionType.READ
    } else {
      return DevicePermissionType.READ_WRITE
    }
  }

  private static modelTypeToDto(model: DevicePermissionType): DevicePermissionDTO {
    if (model === DevicePermissionType.READ) {
      return DevicePermissionDTO.READ
    } else {
      return DevicePermissionDTO.READ_WRITE
    }
  }

  static dtoToModel(dto: DeviceDTO): DeviceInfo {
    const permissions = dto.domains.map(d => new DeviceDomainPermission(d.oid, this.dtoTypeToModel(d.permission)));
    return new DeviceInfo(dto.oid, permissions);
  }

  static modelToDto(model: DeviceInfo): DeviceDTO {
    const domains = model.domains.map(d => ({
      oid: model.id,
      permission: this.modelTypeToDto(d.permission)
    }));
    return {oid: model.id, domains};
  }
}
