import {TenantDTO} from "@frontend-services/identity-management/dto";
import {TenantInfo} from "@frontend-services/identity-management/model";

export class TenantMapper {

  static dtoToModel(dto: TenantDTO): TenantInfo {
    return new TenantInfo(dto.oid, dto.name, dto.email);
  }

  static modelToDto(model: TenantInfo): TenantDTO {
    return {oid: model.id, name: model.name, email: model.email};
  }
}
