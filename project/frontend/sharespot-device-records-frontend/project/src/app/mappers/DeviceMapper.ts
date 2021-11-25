import {DeviceId} from "../model/DeviceId";
import {DeviceIdDTO} from "../dtos/RecordsDTO";

export class DeviceMapper {

  static dtoToModel(dto: DeviceIdDTO): DeviceId {
    return new DeviceId(dto.deviceId);
  }

  static modelToDto(model: DeviceId): DeviceIdDTO {
    return {deviceId: model.deviceId}
  }
}
