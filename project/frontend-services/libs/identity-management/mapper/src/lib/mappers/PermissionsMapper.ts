import {DomainPermissionType} from '@frontend-services/identity-management/model';

export class PermissionsMapper {
  static dtoToModel(dto: string): DomainPermissionType {
    switch (dto) {
      case "device_management:device:read" :
        return DomainPermissionType.READ_DEVICE_INFORMATION;
      case "device_management:device:edit" :
        return DomainPermissionType.DELETE_DEVICE_INFORMATION;
      case "device_management:device:delete" :
        return DomainPermissionType.EDIT_DEVICE_INFORMATION;

      case "data_transformations:transformations:read" :
        return DomainPermissionType.READ_DATA_TRANSFORMATION;
      case "data_transformations:transformations:edit" :
        return DomainPermissionType.EDIT_DATA_TRANSFORMATION;
      case "data_transformations:transformations:delete" :
        return DomainPermissionType.DELETE_DATA_TRANSFORMATION;

      case "data_decoders:decoders:read" :
        return DomainPermissionType.READ_DATA_DECODER;
      case "data_decoders:decoders:edit" :
        return DomainPermissionType.EDIT_DATA_DECODER;
      case "data_decoders:decoders:delete" :
        return DomainPermissionType.DELETE_DATA_DECODER;

      case "identity_management:domains:create" :
        return DomainPermissionType.CREATE_DOMAIN;
      case "identity_management:domains:edit" :
        return DomainPermissionType.EDIT_DOMAIN;
      case "identity_management:domains:read" :
        return DomainPermissionType.READ_DOMAIN;

      case "identity_management:device:write" :
        return DomainPermissionType.EDIT_DEVICE;
      case "identity_management:device:read" :
        return DomainPermissionType.READ_DEVICE;

      case "identity_management:tenant:write" :
        return DomainPermissionType.EDIT_TENANT;
      case "identity_management:tenant:read" :
        return DomainPermissionType.READ_TENANT;

      case "fleet_management:live_data:read" :
        return DomainPermissionType.READ_LIVE_DATA_FLEET_MANAGEMENT;
      case "fleet_management:latest_data:read" :
        return DomainPermissionType.READ_LATEST_DATA_FLEET_MANAGEMENT;
      case "fleet_management:past_data:read" :
        return DomainPermissionType.READ_PAST_DATA_FLEET_MANAGEMENT;

      case "smart_irrigation:garden:create" :
        return DomainPermissionType.CREATE_GARDEN_SMART_IRRIGATION;
      case "smart_irrigation:garden:edit" :
        return DomainPermissionType.EDIT_GARDEN_SMART_IRRIGATION;
      case "smart_irrigation:garden:delete" :
        return DomainPermissionType.DELETE_GARDEN_SMART_IRRIGATION;
      case "smart_irrigation:garden:read" :
        return DomainPermissionType.READ_GARDEN_SMART_IRRIGATION;

      case "smart_irrigation:live_data:read" :
        return DomainPermissionType.READ_LIVE_DATA_SMART_IRRIGATION;
      case "smart_irrigation:latest_data:read" :
        return DomainPermissionType.READ_LATEST_DATA_SMART_IRRIGATION;
      case "smart_irrigation:past_data:read" :
        return DomainPermissionType.READ_PAST_DATA_SMART_IRRIGATION;

      case "smart_irrigation:valve:control" :
        return DomainPermissionType.CONTROL_VALVE_SMART_IRRIGATION;
      default:
        return DomainPermissionType.ERROR;
    }
  }

  static modelToDto(model: DomainPermissionType): string {
    switch (model) {
      case DomainPermissionType.READ_DEVICE_INFORMATION :
        return "device_management:device:read";
      case DomainPermissionType.DELETE_DEVICE_INFORMATION :
        return "device_management:device:edit";
      case DomainPermissionType.EDIT_DEVICE_INFORMATION :
        return "device_management:device:delete";

      case DomainPermissionType.READ_DATA_TRANSFORMATION :
        return "data_transformations:transformations:read";
      case DomainPermissionType.EDIT_DATA_TRANSFORMATION :
        return "data_transformations:transformations:edit";
      case DomainPermissionType.DELETE_DATA_TRANSFORMATION :
        return "data_transformations:transformations:delete";

      case DomainPermissionType.READ_DATA_DECODER :
        return "data_decoders:decoders:read";
      case DomainPermissionType.EDIT_DATA_DECODER :
        return "data_decoders:decoders:edit";
      case DomainPermissionType.DELETE_DATA_DECODER :
        return "data_decoders:decoders:delete";

      case DomainPermissionType.EDIT_DOMAIN :
        return "identity_management:domains:edit";
      case DomainPermissionType.CREATE_DOMAIN :
        return "identity_management:domains:create";
      case DomainPermissionType.READ_DOMAIN :
        return "identity_management:domains:read";

      case DomainPermissionType.EDIT_DEVICE :
        return "identity_management:device:write";
      case DomainPermissionType.READ_DEVICE :
        return "identity_management:device:read";

      case DomainPermissionType.EDIT_TENANT :
        return "identity_management:tenant:write";
      case DomainPermissionType.READ_TENANT :
        return "identity_management:tenant:read";

      case DomainPermissionType.READ_LIVE_DATA_FLEET_MANAGEMENT :
        return "fleet_management:live_data:read";
      case DomainPermissionType.READ_LATEST_DATA_FLEET_MANAGEMENT :
        return "fleet_management:latest_data:read";
      case DomainPermissionType.READ_PAST_DATA_FLEET_MANAGEMENT :
        return "fleet_management:past_data:read";

      case DomainPermissionType.CREATE_GARDEN_SMART_IRRIGATION :
        return "smart_irrigation:garden:create";
      case DomainPermissionType.EDIT_GARDEN_SMART_IRRIGATION :
        return "smart_irrigation:garden:edit";
      case DomainPermissionType.DELETE_GARDEN_SMART_IRRIGATION :
        return "smart_irrigation:garden:delete";
      case DomainPermissionType.READ_GARDEN_SMART_IRRIGATION :
        return "smart_irrigation:garden:read";

      case DomainPermissionType.READ_LIVE_DATA_SMART_IRRIGATION :
        return "smart_irrigation:live_data:read";
      case DomainPermissionType.READ_LATEST_DATA_SMART_IRRIGATION :
        return "smart_irrigation:latest_data:read";
      case DomainPermissionType.READ_PAST_DATA_SMART_IRRIGATION :
        return "smart_irrigation:past_data:read";

      case DomainPermissionType.CONTROL_VALVE_SMART_IRRIGATION :
        return "smart_irrigation:valve:control";
      case DomainPermissionType.ERROR:
        return 'error';
    }
  }
}
