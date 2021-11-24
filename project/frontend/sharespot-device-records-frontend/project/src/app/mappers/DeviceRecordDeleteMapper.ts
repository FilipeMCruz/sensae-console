import {DeviceRecordDelete} from "../dtos/RecordsDTO";
import {DeviceMapper} from "./DeviceMapper";
import {Device} from "../model/Device";

export class DeviceRecordDeleteMapper {
  static dtoToModel(dto: DeviceRecordDelete): Device {
    return DeviceMapper.dtoToModel(dto.delete);
  }
}
