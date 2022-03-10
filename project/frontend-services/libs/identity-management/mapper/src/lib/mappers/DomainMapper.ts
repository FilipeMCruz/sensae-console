import {DomainDTO, DomainInfoDTO} from "@frontend-services/identity-management/dto";
import {Domain, DomainInfo} from "@frontend-services/identity-management/model";
import {TenantMapper} from "./TenantMapper";
import {DeviceMapper} from "@frontend-services/identity-management/mapper";

export class DomainMapper {

  static dtoToModel(dto: DomainDTO): Domain {
    return new Domain(dto.oid, dto.name, dto.path);
  }

  static modelToDto(model: Domain): DomainDTO {
    return {oid: model.id, name: model.name, path: model.path};
  }

  static dtoDetailsToDto(dto: DomainInfoDTO): DomainInfo {
    const tenants = dto.tenants.map(t => TenantMapper.dtoToModel(t));
    const devices = dto.devices.map(t => DeviceMapper.dtoToModel(t));
    return new DomainInfo(this.dtoToModel(dto.domain), tenants, devices)
  }
}
