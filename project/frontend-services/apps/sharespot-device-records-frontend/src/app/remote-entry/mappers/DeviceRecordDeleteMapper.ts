import {DeviceRecordDelete} from "../dtos/RecordsDTO";
import {DeviceMapper} from "./DeviceMapper";
import {DeviceId} from "../model/DeviceId";

export class DeviceRecordDeleteMapper {
  static dtoToModel(dto: DeviceRecordDelete): DeviceId {
    return DeviceMapper.dtoToModel(dto.delete);
  }
}
