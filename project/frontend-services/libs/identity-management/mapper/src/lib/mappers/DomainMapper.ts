import {DomainDTO, DomainInfoDTO,} from '@frontend-services/identity-management/dto';
import {Domain, DomainInfo,} from '@frontend-services/identity-management/model';
import {TenantMapper} from './TenantMapper';
import {DeviceMapper} from "./DeviceMapper";
import {PermissionsMapper} from "./PermissionsMapper";

export class DomainMapper {
  static dtoToModel(dto: DomainDTO): Domain {
    const permissionTypes = dto.permissions.map(p => PermissionsMapper.dtoToModel(p));
    return new Domain(dto.oid, dto.name, dto.path, permissionTypes);
  }

  static modelToDto(model: Domain): DomainDTO {
    const map = model.permissions.map(p => PermissionsMapper.modelToDto(p));
    return {oid: model.id, name: model.name, path: model.path, permissions: map};
  }

  static dtoDetailsToDto(dto: DomainInfoDTO): DomainInfo {
    const tenants = dto.tenants.map((t) => TenantMapper.dtoToModel(t));
    const devices = dto.devices.map((t) => DeviceMapper.dtoToModel(t));
    return new DomainInfo(this.dtoToModel(dto.domain), tenants, devices);
  }
}
