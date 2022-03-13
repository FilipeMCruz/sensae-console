import { DomainPermissionType } from '@frontend-services/identity-management/model';

export class PermissionsMapper {
  static dtoToModel(dto: string): DomainPermissionType {
    switch (dto) {
      case 'device_records:records:read':
        return DomainPermissionType.READ_DEVICE_RECORDS;
      case 'device_records:records:write':
        return DomainPermissionType.WRITE_DEVICE_RECORDS;
      case 'data_transformations:transformations:read':
        return DomainPermissionType.READ_DATA_TRANSFORMATIONS;
      case 'data_transformations:transformations:write':
        return DomainPermissionType.WRITE_DATA_TRANSFORMATIONS;
      case 'fleet_management:read':
        return DomainPermissionType.READ_FLEET_MANAGEMENT;
      case 'identity_management:domains:create':
        return DomainPermissionType.WRITE_DOMAINS;
      case 'identity_management:domains:read':
        return DomainPermissionType.READ_DOMAINS;
      case 'identity_management:tenant:read':
        return DomainPermissionType.READ_TENANT;
      case 'identity_management:device:read':
        return DomainPermissionType.READ_DEVICE;
      case 'identity_management:tenant:write':
        return DomainPermissionType.WRITE_TENANT;
      case 'identity_management:device:write':
        return DomainPermissionType.WRITE_DEVICE;
      default:
        return DomainPermissionType.ERROR;
    }
  }

  static modelToDto(model: DomainPermissionType): string {
    switch (model) {
      case DomainPermissionType.READ_DEVICE_RECORDS:
        return 'device_records:records:read';
      case DomainPermissionType.WRITE_DEVICE_RECORDS:
        return 'device_records:records:write';
      case DomainPermissionType.READ_DATA_TRANSFORMATIONS:
        return 'data_transformations:transformations:read';
      case DomainPermissionType.WRITE_DATA_TRANSFORMATIONS:
        return 'data_transformations:transformations:write';
      case DomainPermissionType.READ_FLEET_MANAGEMENT:
        return 'fleet_management:read';
      case DomainPermissionType.WRITE_DOMAINS:
        return 'identity_management:domains:create';
      case DomainPermissionType.READ_DOMAINS:
        return 'identity_management:domains:read';
      case DomainPermissionType.READ_TENANT:
        return 'identity_management:tenant:read';
      case DomainPermissionType.READ_DEVICE:
        return 'identity_management:device:read';
      case DomainPermissionType.WRITE_TENANT:
        return 'identity_management:tenant:write';
      case DomainPermissionType.WRITE_DEVICE:
        return 'identity_management:device:write';
      case DomainPermissionType.ERROR:
        return 'error';
    }
  }
}
