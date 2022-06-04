import {TenantProfileDTO, TenantProfileResultDTO, TenantProfileUpdateResultDTO} from "../dto/CredentialsDTO";
import {TenantProfile} from "../model/TenantProfile";

export class ProfileMapper {
  static dtoToUpdateModel(dto: TenantProfileUpdateResultDTO): TenantProfile {
    return ProfileMapper.dtoToProfileModel(dto.updateTenant)
  }

  static dtoToQueryModel(dto: TenantProfileResultDTO): TenantProfile {
    return ProfileMapper.dtoToProfileModel(dto.profile)
  }

  private static dtoToProfileModel(dto: TenantProfileDTO): TenantProfile {
    return new TenantProfile(dto.oid, dto.name, dto.email, dto.phoneNumber);
  }

  static toUpdateTenant(tenant: TenantProfile) {
    return {instructions: {name: tenant.name, phoneNumber: tenant.phoneNumber}};
  }
}
