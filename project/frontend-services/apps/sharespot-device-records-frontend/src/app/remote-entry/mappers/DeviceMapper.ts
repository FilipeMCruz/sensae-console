import {DeviceDTO} from "../dtos/RecordsDTO";
import {Device} from "../model/Device";

export class DeviceMapper {

  static dtoToModel(dto: DeviceDTO): Device {
    return new Device(dto.id, dto.name);
  }

  static modelToDto(model: Device): DeviceDTO {
    return {id: model.id, name: model.name}
  }
}
